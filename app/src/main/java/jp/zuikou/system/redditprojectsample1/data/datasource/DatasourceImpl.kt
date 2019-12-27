package jp.zuikou.system.redditprojectsample1.data.datasource

import android.annotation.SuppressLint
import io.reactivex.Single
import jp.zuikou.system.kintaiapp.presentation.extensions.DateFormat
import jp.zuikou.system.kintaiapp.presentation.extensions.convertStringToLocalDateTimeJoda
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonAccessTokenMapper
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonPostMapper
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonRSubSubcribersMapper
import jp.zuikou.system.redditprojectsample1.data.service.LoginServiceAPI
import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import org.joda.time.LocalDateTime
import org.koin.core.context.GlobalContext

class DatasourceImpl(
    private var service: PostsServiceAPI,
    private val accessTokenService: LoginServiceAPI
) : Datasource {
    @SuppressLint("CheckResult")
    override fun getPagedListPosts(
        subReddit: String?,
        type: String?,
        page: String?
    ): Single<Pair<Pagination, List<PostEntity>>> {
        if (isAccessTokenIsExpired()) {
            return getAccessToken()
                .flatMap {
                    getPagedList(subReddit, type, page)
                }
        }
        return getPagedList(subReddit, type, page)
    }

    private fun getPagedList(
        subReddit: String?,
        type: String?,
        page: String?
    ): Single<Pair<Pagination, List<PostEntity>>> {
        return service.getPagedListPosts(subReddit, type, page)
            .map { json ->
                val list = json.data?.children
                    ?.map { it.data }
                    ?.mapNotNull { it }

                val posts = JsonPostMapper.transformToList(list ?: emptyList())

                Pair(Pagination(json.data?.after ?: ""), posts)
            }

    }

    override fun getPagedListMineSubscribers(
        nextPage: String?,
        limit: Int?
    ): Single<Pair<Pagination, List<RSubSubcribersEntity>>> {
        if (isAccessTokenIsExpired()) {
            return getAccessToken()
                .flatMap {
                    getListMineSubscribers(nextPage, limit)
                }
        }
        return getListMineSubscribers(nextPage, limit)
    }

    private fun getListMineSubscribers(
        nextPage: String?,
        limit: Int?
    ): Single<Pair<Pagination, List<RSubSubcribersEntity>>> =
        service.getPagedListMineSubscribers(nextPage = nextPage, limit = limit)
            .map { json ->
                val list = json.data?.children
                    ?.map { it.data }
                    ?.mapNotNull { it }

                val rsubcribersList = JsonRSubSubcribersMapper.transformToList(list ?: emptyList())
                Pair(Pagination(json.data?.after), rsubcribersList)
            }

    private fun getAccessToken(): Single<AccessTokenEntity> =
        accessTokenService.refreshingTheToken()
            .map {
                val accessTokenEntity = JsonAccessTokenMapper.transformTo(it)
                SharedPreferenceSingleton.setAccessTokenEntity(accessTokenEntity)
                service = GlobalContext.get().koin.get()
                accessTokenEntity
            }

    private fun isAccessTokenIsExpired(): Boolean {
        val nowDateTime = LocalDateTime.now()
        val savedAccessTokenEntity = SharedPreferenceSingleton.getAccessTokenEntity()
        val getSavedDateTime = SharedPreferenceSingleton.getAccessTokenEntity()
            ?.expiresIn?.convertStringToLocalDateTimeJoda(DateFormat.FULL_LONG_DATE_FORMAT_NOSPACE_NOCOLON)
        return savedAccessTokenEntity != null && nowDateTime.isAfter(getSavedDateTime)
    }
}
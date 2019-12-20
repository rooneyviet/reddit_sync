package jp.zuikou.system.redditprojectsample1.data.datasource

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonPostMapper
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonRSubSubcribersMapper
import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import timber.log.Timber

class DatasourceImpl(private val service: PostsServiceAPI): Datasource {
    override fun getPagedListPosts(
        subReddit: String?,
        type: String?,
        page: String
    ): Single<Pair<Pagination, List<PostEntity>>> =
        service.getPagedListPosts(subReddit, type,page) //TODO subReddit, type, page
            .map { json ->
                val list = json.data?.children
                    ?.map { it.data }
                    ?.mapNotNull { it }

                val posts = JsonPostMapper.transformToList(list ?: emptyList())

                Pair(Pagination(json.data?.after ?: ""), posts)

            }

    override fun getPagedListMineSubscribers(nextPage: String?,
                                             limit: Int?): Single<Pair<Pagination, List<RSubSubcribersEntity>>> =
        service.getPagedListMineSubscribers()
            .map { json->
                val list = json.data?.children
                    ?.map { it.data }
                    ?.mapNotNull { it }

                val rsubcribersList = JsonRSubSubcribersMapper.transformToList(list?: emptyList())
                Pair(Pagination(json.data?.after?: ""), rsubcribersList)
            }
}
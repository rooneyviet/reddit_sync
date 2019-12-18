package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetPostsRequestValue
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSource.Companion.mType
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSource.Companion.subReddit

class PostsDataSource(
    getPostByCommunity: UseCase<GetPostsRequestValue,
            Pair<Pagination, List<PostEntity>>>,
    compositeDisposable: CompositeDisposable
) : GenericDataSource<PostEntity, GetPostsRequestValue>(getPostByCommunity,
    compositeDisposable, ::getPostsByRequestValue) {

    companion object {
        //TODO in the future view can be pass others community, backend offer support
        //const val COMMUNITY = "r/Android"
        var subReddit: String? = null
        var mType: String? = null
    }
}


fun getPostsByRequestValue(pagination: Pagination): GetPostsRequestValue =
    GetPostsRequestValue(page = pagination.nextPage, subReddit = subReddit, type = mType)

//fun getMapper(list: List<PostEntity>): List<Presentation> = PresentationPostMapper.transformFromList(list)

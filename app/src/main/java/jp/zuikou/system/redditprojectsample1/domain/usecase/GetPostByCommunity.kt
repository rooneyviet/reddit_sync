package jp.zuikou.system.redditprojectsample1.domain.usecase

import io.reactivex.Flowable
import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.RequestValueNullPointException
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.PostRepository
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetPostsRequestValue

class GetPostByCommunity(private val repository: PostRepository): UseCase<GetPostsRequestValue, Pair<Pagination, List<PostEntity>>>() {
    override fun executeUseCase(requestValues: GetPostsRequestValue?): Single<Pair<Pagination, List<PostEntity>>> {
        if (requestValues == null){ throw RequestValueNullPointException() }
        return repository.getPagedListPosts(requestValues.subReddit, requestValues.type, requestValues.page)
    }
}
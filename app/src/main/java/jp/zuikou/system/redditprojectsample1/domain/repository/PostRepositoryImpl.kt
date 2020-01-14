package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.datasource.Datasource
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity

class PostRepositoryImpl(private val datasource: Datasource): PostRepository {
    override fun getPagedListPosts(
        subReddit: String?,
        type: String?,
        page: String?
    ): Single<Pair<Pagination, List<PostEntity>>> =
        datasource.getPagedListPosts(subReddit, type, page)

    override fun votePost(isUpvote: Boolean, postId: String): Single<Void> =
        datasource.votePost(isUpvote, postId)
}
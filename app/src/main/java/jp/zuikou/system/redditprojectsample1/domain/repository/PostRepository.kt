package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity

interface PostRepository {
    fun getPagedListPosts(subReddit: String? = null,
                          type: String? = null,
                          page: String?=null ): Single<Pair<Pagination, List<PostEntity>>>

    fun votePost(isUpvote: Boolean,
                 postId: String): Completable
}
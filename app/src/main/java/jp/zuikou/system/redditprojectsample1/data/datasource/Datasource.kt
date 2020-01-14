package jp.zuikou.system.redditprojectsample1.data.datasource

import io.reactivex.Completable
import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity

interface Datasource {
    fun getPagedListPosts(subReddit: String? = null,
                          type: String? = null,
                          page: String? = null): Single<Pair<Pagination, List<PostEntity>>>

    fun getPagedListMineSubscribers(nextPage: String? = null, limit: Int? = 100): Single<Pair<Pagination, List<RSubSubcribersEntity>>>


    fun votePost(isUpvote: Boolean?,
                 postId: String): Completable
}
package jp.zuikou.system.redditprojectsample1.data.datasource

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity

interface Datasource {
    fun getPagedListPosts(subReddit: String? = null,
                          type: String? = null,
                          page: String): Single<Pair<Pagination, List<PostEntity>>>
}
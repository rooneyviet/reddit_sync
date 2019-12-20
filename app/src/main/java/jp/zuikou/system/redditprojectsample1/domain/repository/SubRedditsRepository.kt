package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity

interface SubRedditsRepository {
    fun getPagedListMineSubscribers(nextPage: String?, limit: Int? = 100): Single<Pair<Pagination, List<RSubSubcribersEntity>>>
}
package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.datasource.Datasource
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity

class SubRedditsRepositoryImpl(val datasource: Datasource): SubRedditsRepository {
    override fun getPagedListMineSubscribers(nextPage: String?, limit: Int?): Single<Pair<Pagination, List<RSubSubcribersEntity>>> =
        datasource.getPagedListMineSubscribers(nextPage = nextPage, limit =  limit)

}
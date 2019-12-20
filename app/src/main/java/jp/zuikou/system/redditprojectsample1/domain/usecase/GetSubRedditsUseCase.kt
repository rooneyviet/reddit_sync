package jp.zuikou.system.redditprojectsample1.domain.usecase

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.RequestValueNullPointException
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.SubRedditsRepository
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetSubRedditsRequestValue

class GetSubRedditsUseCase(private val repository: SubRedditsRepository): UseCase<GetSubRedditsRequestValue, Pair<Pagination, List<RSubSubcribersEntity>>>() {
    override fun executeUseCase(requestValues: GetSubRedditsRequestValue?): Single<Pair<Pagination, List<RSubSubcribersEntity>>> {
        if (requestValues == null){ throw RequestValueNullPointException() }
        return repository.getPagedListMineSubscribers(requestValues.nextPage)
    }
}
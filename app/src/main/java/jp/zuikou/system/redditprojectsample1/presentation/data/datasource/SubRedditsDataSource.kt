package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetSubRedditsRequestValue
import jp.zuikou.system.redditprojectsample1.domain.usecase.GetSubRedditsUseCase

class SubRedditsDataSource(getSubRedditsUseCase: GetSubRedditsUseCase,
                           compositeDisposable: CompositeDisposable
) : GenericDataSource<RSubSubcribersEntity, GetSubRedditsRequestValue>(getSubRedditsUseCase,
    compositeDisposable, ::getSubRedditsByRequestValue) {

    companion object {
        //TODO in the future view can be pass others community, backend offer support
        //const val COMMUNITY = "r/Android"

    }
}

fun getSubRedditsByRequestValue(pagination: Pagination): GetSubRedditsRequestValue =
    GetSubRedditsRequestValue(nextPage = pagination.nextPage)
package jp.zuikou.system.redditprojectsample1.presentation.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.RequestValues
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase

object UseCaseHandler {
    fun <RV : RequestValues, T> execute(useCase: UseCase<RV, T>, values: RV? = null): Single<T> {
        useCase.setRequestValues(values)
        return useCase.run()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
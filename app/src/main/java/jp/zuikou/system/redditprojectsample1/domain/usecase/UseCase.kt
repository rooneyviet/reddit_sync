package jp.zuikou.system.redditprojectsample1.domain.usecase

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.RequestValues

abstract class UseCase<in RV : RequestValues, T> {

    private var requestValue: RV? = null

    fun setRequestValues(requestValues: RV?) {
        this.requestValue = requestValues
    }

    fun run(): Single<T> {
        return executeUseCase(requestValue)
    }

    abstract fun executeUseCase(requestValues: RV? = null): Single<T>
}
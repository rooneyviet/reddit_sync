package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.RequestValues
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase
import jp.zuikou.system.redditprojectsample1.presentation.util.UseCaseHandler
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class GenericDataSource<Response, RV: RequestValues>(
    private val useCase: UseCase<RV, Pair<Pagination, List<Response>>>,
    private val compositeDisposable: CompositeDisposable,
    private val requestValues: (pagination: Pagination) -> RV//,
   // private val mapper: (List<Input>) -> List<Response>
) : PageKeyedDataSource<Pagination, Response>() {

    val networkState = MutableLiveData<NetworkState>()

    private var retry: (() -> Any)? = null

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Pagination>,
        callback: LoadInitialCallback<Pagination, Response>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(useCaseCall()
            .subscribe({ result ->
                networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(result.second, null,
                    if (result.first.nextPage.isNullOrEmpty()) null else result.first)
            }, { throwable ->
                handleError(throwable) { loadInitial(params, callback) }
            })
        )
    }

    override fun loadAfter(
        params: LoadParams<Pagination>,
        callback: LoadCallback<Pagination, Response>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(useCaseCall(params.key)
            .subscribe({ result ->
                networkState.postValue(NetworkState.SUCCESS)
                callback.onResult(result.second, if (result.first.nextPage.isNullOrEmpty()) null else result.first)
            }, { throwable ->
                handleError(throwable) { loadAfter(params, callback) }
            })
        )
    }

    private fun useCaseCall(pagination: Pagination = Pagination()): Single<Pair<Pagination, List<Response>>> =
        UseCaseHandler.execute(useCase, requestValues.invoke(pagination))
            .map {

                //val result = mapper.invoke(it.second)
                Pair(it.first, it.second)
            }
            //.compose(ErrorHandler())

    @Suppress("UNUSED_EXPRESSION")
    private fun handleError(throwable: Throwable?, functionCall: () -> Unit) {
        retry = functionCall
        if (throwable is UnknownHostException || throwable is SocketTimeoutException){
            networkState.postValue(NetworkState.NO_INTERNET)
        }
    }

    override fun loadBefore(
        params: LoadParams<Pagination>,
        callback: LoadCallback<Pagination, Response>
    ) {
        //Ignore
    }
}
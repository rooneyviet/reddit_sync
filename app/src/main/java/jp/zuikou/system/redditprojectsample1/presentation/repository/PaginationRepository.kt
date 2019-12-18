package jp.zuikou.system.redditprojectsample1.presentation.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState

interface PaginationRepository<T> {

    fun getList(): LiveData<PagedList<T>>

    fun getNetworkState(): LiveData<NetworkState>

    fun retry()

    fun refresh()

    fun setCompositeDisposable(disposable: CompositeDisposable)
}
package jp.zuikou.system.redditprojectsample1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.repository.PaginationRepository

class MainViewModel (private val subcribersRepositoryPresent: PaginationRepository<RSubSubcribersEntity>): BaseViewModel() {
    init {
        subcribersRepositoryPresent.setCompositeDisposable(compositeDisposable)
    }

    fun getSubcribersList(): LiveData<PagedList<RSubSubcribersEntity>> = subcribersRepositoryPresent.getList()

    fun getNetworkStateList(): LiveData<NetworkState> = subcribersRepositoryPresent.getNetworkState()

    fun retryGetSubcribersList() = subcribersRepositoryPresent.retry()

    fun refreshGetSubcribersList() = subcribersRepositoryPresent.refresh()
}
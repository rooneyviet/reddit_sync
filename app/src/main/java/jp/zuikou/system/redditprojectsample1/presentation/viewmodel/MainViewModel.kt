package jp.zuikou.system.redditprojectsample1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditRequest
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditSortByDayEnum
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditTypeEnum
import jp.zuikou.system.redditprojectsample1.presentation.repository.PaginationRepository
import org.koin.core.context.GlobalContext

class MainViewModel (private var subcribersRepositoryPresent: PaginationRepository<RSubSubcribersEntity>): BaseViewModel() {
    init {
        subcribersRepositoryPresent.setCompositeDisposable(compositeDisposable)
    }

    val currentSubRedditRequestValue : SubRedditRequest
        get() = currentSubRedditRequestLiveData.value?: SubRedditRequest("")

    private val currentSubRedditRequestLiveData = MutableLiveData<SubRedditRequest>()


    fun saveCurrentSubCurrentRequest(subreddit:String?= null, subtype: SubRedditTypeEnum? = null, subSortDate: SubRedditSortByDayEnum? = null){
        val currentValue = SubRedditRequest(subreddit?: "", subtype, subSortDate)
        currentSubRedditRequestLiveData.postValue(currentValue)
    }


    fun getSubcribersList(isReset: Boolean = false): LiveData<PagedList<RSubSubcribersEntity>> {
        if(isReset){
            subcribersRepositoryPresent = GlobalContext.get().koin.get()
        }
        return subcribersRepositoryPresent.getList()
    }

    fun getNetworkStateList(): LiveData<NetworkState> = subcribersRepositoryPresent.getNetworkState()

    fun retryGetSubcribersList() = subcribersRepositoryPresent.retry()

    fun refreshGetSubcribersList() = subcribersRepositoryPresent.refresh()
}
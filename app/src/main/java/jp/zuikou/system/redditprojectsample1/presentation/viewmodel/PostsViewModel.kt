package jp.zuikou.system.redditprojectsample1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresent

class PostsViewModel(private val repository: PostRepositoryPresent) : BaseViewModel() {

    init {
        repository.setCompositeDisposable(compositeDisposable)
    }

    fun getPosts(subreddit: String? = null, type: String? = null): LiveData<PagedList<PostEntity>> = repository.getList(subreddit, type)

    fun getNetworkState(): LiveData<NetworkState> = repository.getNetworkState()

    fun retry() = repository.retry()

    fun refresh() = repository.refresh()
}
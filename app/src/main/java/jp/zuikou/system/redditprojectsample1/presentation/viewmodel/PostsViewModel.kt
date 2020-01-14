package jp.zuikou.system.redditprojectsample1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.schedulers.Schedulers
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.PostRepository
import jp.zuikou.system.redditprojectsample1.presentation.SingleLiveEvent
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.model.PostVoteRequest
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresent
import org.koin.core.context.GlobalContext
import timber.log.Timber

class PostsViewModel(private var repositoryPresentation: PostRepositoryPresent, private val postRepositoryDomain: PostRepository) : BaseViewModel() {

    init {
        repositoryPresentation.setCompositeDisposable(compositeDisposable)
    }

    val postVoteLiveData = SingleLiveEvent<PostVoteRequest?>()

    fun getPosts(subreddit: String? = null, type: String? = null, isReset: Boolean = false): LiveData<PagedList<PostEntity>> {
        if(isReset){
            //repository.setCompositeDisposable(compositeDisposable)
            repositoryPresentation = GlobalContext.get().koin.get()
        }
        return repositoryPresentation.getList(subreddit, type)
    }


    fun getNetworkState(): LiveData<NetworkState> = repositoryPresentation.getNetworkState()

    fun retry() = repositoryPresentation.retry()

    fun refresh() = repositoryPresentation.refresh()

    fun votePost(
        postVoteRequest: PostVoteRequest) = compositeDisposable
            .add(postRepositoryDomain.votePost(postVoteRequest.isUpvote, postVoteRequest.postId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                Timber.d(postVoteRequest.toString())
                postVoteLiveData.postValue(postVoteRequest)
            }, {
                //postVoteLiveData.postValue(PostVoteRequest(!isUpvote, postId))
            })
        )

}
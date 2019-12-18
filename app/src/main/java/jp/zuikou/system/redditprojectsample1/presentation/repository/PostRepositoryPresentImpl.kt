package jp.zuikou.system.redditprojectsample1.presentation.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSource
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSourceFactory
import kotlin.properties.Delegates

class PostRepositoryPresentImpl(
    private val factory: PostsDataSourceFactory,
    private val pagedListConfig: PagedList.Config): PostRepositoryPresent {//private val pagedListConfig: PagedList.Config

    private var posts: LiveData<PagedList<PostEntity>> by Delegates.notNull()

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build()

    init {
        //resetList()
        posts = LivePagedListBuilder(factory, pagedListConfig).build()
    }

    override fun getList(subReddit: String?, type: String?): LiveData<PagedList<PostEntity>> {
        PostsDataSource.mType = type
        PostsDataSource.subReddit = subReddit
        return posts
    }

    override fun getList(): LiveData<PagedList<PostEntity>> {
        return posts
    }

    /*override fun resetList() {
        posts = LivePagedListBuilder(factory, getPagedListConfig()).build()
    }*/

    override fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap(factory.dataSource) { it.networkState }

    override fun retry() {
        factory.dataSource.value?.retry()
    }

    override fun refresh() {
        factory.dataSource.value?.invalidate()
    }

    override fun setCompositeDisposable(disposable: CompositeDisposable) {
        factory.compositeDisposable = disposable
    }

}
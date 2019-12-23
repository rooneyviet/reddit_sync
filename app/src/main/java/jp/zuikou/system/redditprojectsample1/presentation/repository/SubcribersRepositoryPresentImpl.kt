package jp.zuikou.system.redditprojectsample1.presentation.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.SubRedditsDataSourceFactory
import kotlin.properties.Delegates

class SubcribersRepositoryPresentImpl(val factory: SubRedditsDataSourceFactory): PaginationRepository<RSubSubcribersEntity> {

    private var subreddits: LiveData<PagedList<RSubSubcribersEntity>> by Delegates.notNull()

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build()

    init {
        subreddits = LivePagedListBuilder(factory, getPagedListConfig()).build()
    }
    override fun getList(): LiveData<PagedList<RSubSubcribersEntity>> {
        return subreddits
    }

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
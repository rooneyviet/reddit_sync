package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.domain.usecase.GetSubRedditsUseCase

class SubRedditsDataSourceFactory (private val getSubcriberSubRedditUseCase:
                                   GetSubRedditsUseCase
)
    : DataSource.Factory<Pagination, RSubSubcribersEntity>() {

    var compositeDisposable = CompositeDisposable()

    val dataSource = MutableLiveData<SubRedditsDataSource>()

    override fun create(): DataSource<Pagination, RSubSubcribersEntity> {
        val dataSource = SubRedditsDataSource(getSubcriberSubRedditUseCase, compositeDisposable)
        this.dataSource.postValue(dataSource)
        return dataSource
    }

}
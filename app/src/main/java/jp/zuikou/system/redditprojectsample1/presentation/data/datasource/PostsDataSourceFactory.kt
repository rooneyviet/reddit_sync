package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetPostsRequestValue
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase
import kotlin.properties.Delegates

class PostsDataSourceFactory(private val getPostBySubReddit:
                                UseCase<GetPostsRequestValue,
                                        Pair<Pagination, List<PostEntity>>>
)
    : DataSource.Factory<Pagination, PostEntity>() {

    var compositeDisposable: CompositeDisposable by Delegates.notNull()

    val dataSource = MutableLiveData<PostsDataSource>()

    override fun create(): DataSource<Pagination, PostEntity> {
        val dataSource = PostsDataSource(getPostBySubReddit, compositeDisposable)
        this.dataSource.postValue(dataSource)
        return dataSource
    }

}
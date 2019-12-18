package jp.zuikou.system.redditprojectsample1.di

import android.widget.ImageView
import jp.zuikou.system.redditprojectsample1.SubRedditFragment.Companion.PROPERTY_PAGED_LIST
import jp.zuikou.system.redditprojectsample1.data.datasource.Datasource
import jp.zuikou.system.redditprojectsample1.data.datasource.DatasourceImpl
import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.PostRepository
import jp.zuikou.system.redditprojectsample1.domain.repository.PostRepositoryImpl
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetPostsRequestValue
import jp.zuikou.system.redditprojectsample1.domain.usecase.GetPostByCommunity
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSourceFactory
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresent
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresentImpl
import jp.zuikou.system.redditprojectsample1.presentation.ui.PostsPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.PostsViewModel
import org.koin.android.BuildConfig
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectBasicFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule,
            useCaseModule,
            repositoryModule,
            postsModule,
            apiModule )
    )
}

val viewModelModule= module {
    /*viewModel { HealthCareExerciseViewModel( get()) }*/

}

val useCaseModule = module {
    //factory { LoginUseCase(loginRepository = get()) }
}

val repositoryModule = module {
    /*factory { ExerciseRepositoryImpl(exerciseCallAPI = get()) as ExerciseRepository }*/
}

val postsModule: Module = module {
    //factory { LoginDatasourceImpl(loginService = get()) as LoginDatasource }

    single<Datasource> { DatasourceImpl(get()) }
    single { PostsViewModel(get() ) }
    single<PostRepositoryPresent> { PostRepositoryPresentImpl (get(), getProperty(PROPERTY_PAGED_LIST)) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<UseCase<GetPostsRequestValue, Pair<Pagination, List<PostEntity>>>>(named(USE_CASE_POST)) { GetPostByCommunity(get()) }

    single { PostsDataSourceFactory(get(named(USE_CASE_POST))) }

    factory { (retryCallback: () -> Unit,
                  clickItem: (post: PostEntity, image: ImageView) -> Unit) ->
        PostsPagedListAdapter(retryCallback, clickItem) }

}

private const val USE_CASE_POST = "useCasePost"


val apiModule: Module = module {
    single<Retrofit>(named(NORMAL_NAMESPACE)) {createNetworkClient()}
    single { providePostService(get (named(NORMAL_NAMESPACE))) }
    /*single<ExerciseCallAPI> { provideExerciseCallService(get(NORMAL_NAMESPACE))}*/
}

private fun providePostService(retrofit: Retrofit): PostsServiceAPI = retrofit.create(PostsServiceAPI::class.java)

const val NORMAL_NAMESPACE = "UNAUTHENTICATED_NAMESPACE"


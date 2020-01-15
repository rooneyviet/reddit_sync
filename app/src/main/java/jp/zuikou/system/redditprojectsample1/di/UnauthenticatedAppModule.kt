package jp.zuikou.system.redditprojectsample1.di

import android.widget.ImageView
import jp.zuikou.system.redditprojectsample1.SubRedditFragment.Companion.PROPERTY_PAGED_LIST
import jp.zuikou.system.redditprojectsample1.data.datasource.Datasource
import jp.zuikou.system.redditprojectsample1.data.datasource.DatasourceImpl
import jp.zuikou.system.redditprojectsample1.data.datasource.LoginDatasource
import jp.zuikou.system.redditprojectsample1.data.datasource.LoginDatasourceImpl
import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
import jp.zuikou.system.redditprojectsample1.domain.Pagination
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.*
import jp.zuikou.system.redditprojectsample1.domain.requestvalue.GetPostsRequestValue
import jp.zuikou.system.redditprojectsample1.domain.usecase.GetPostByCommunityUseCase
import jp.zuikou.system.redditprojectsample1.domain.usecase.GetSubRedditsUseCase
import jp.zuikou.system.redditprojectsample1.domain.usecase.UseCase
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.PostsDataSourceFactory
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.SubRedditsDataSourceFactory
import jp.zuikou.system.redditprojectsample1.presentation.data.model.PostVoteRequest
import jp.zuikou.system.redditprojectsample1.presentation.navigation_drawer.DrawerLayoutPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.repository.PaginationRepository
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresent
import jp.zuikou.system.redditprojectsample1.presentation.repository.PostRepositoryPresentImpl
import jp.zuikou.system.redditprojectsample1.presentation.repository.SubcribersRepositoryPresentImpl
import jp.zuikou.system.redditprojectsample1.presentation.ui.PostsPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.MainViewModel
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectBasicFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            postsModule,
            retrofitModule,
            mineSubcribersModule,
            accessTokenLoginModule
        )
    )
}

val viewModelModule = module {
    /*viewModel { HealthCareExerciseViewModel( get()) }*/

}

val useCaseModule = module {
    //factory { LoginUseCase(loginRepository = get()) }
}

val repositoryModule = module {
    /*factory { ExerciseRepositoryImpl(exerciseCallAPI = get()) as ExerciseRepository }*/
}

val postsModule: Module = module {
    factory<Datasource> { DatasourceImpl(get(), get()) }
    viewModel { PostsViewModel(get(), get()) }
    factory<PostRepositoryPresent> {
        PostRepositoryPresentImpl(
            get(),
            getProperty(PROPERTY_PAGED_LIST)
        )
    }
    factory<PostRepository> { PostRepositoryImpl(get()) }
    factory<UseCase<GetPostsRequestValue, Pair<Pagination, List<PostEntity>>>>(named(USE_CASE_POST)) {
        GetPostByCommunityUseCase(
            get()
        )
    }

    factory { PostsDataSourceFactory(get(named(USE_CASE_POST))) }

    factory { (retryCallback: () -> Unit,
                  clickItem: (post: PostEntity, image: ImageView) -> Unit,
                  upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit) ->
        PostsPagedListAdapter(retryCallback, clickItem, upvoteDownvote)
    }



}

val mineSubcribersModule: Module = module {
    //single<Datasource> { DatasourceImpl(get()) }
    single { MainViewModel(get()) }
    single<PaginationRepository<RSubSubcribersEntity>> { SubcribersRepositoryPresentImpl(get()) }
    single<SubRedditsRepository> { SubRedditsRepositoryImpl(get()) }
    single<GetSubRedditsUseCase>(named(USE_CASE_SUBSCRIBER)) { GetSubRedditsUseCase(get()) }

    single { SubRedditsDataSourceFactory(get(named(USE_CASE_SUBSCRIBER))) }

    factory { (subClicked: (subreddit: String) -> Unit) ->
        DrawerLayoutPagedListAdapter(subClicked)
    }

}


val accessTokenLoginModule: Module = module {
    //single<Datasource> { DatasourceImpl(get()) }
    single<LoginDatasource> { LoginDatasourceImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}

private const val USE_CASE_POST = "useCasePost"
private const val USE_CASE_SUBSCRIBER = "useCaseSubcribers"


val apiModule: Module = module {
    single<Retrofit>(named(NORMAL_NAMESPACE)) { createNetworkClient() }
    single { providePostService(get(named(NORMAL_NAMESPACE))) }
    /*single<ExerciseCallAPI> { provideExerciseCallService(get(NORMAL_NAMESPACE))}*/
}

private fun providePostService(retrofit: Retrofit): PostsServiceAPI =
    retrofit.create(PostsServiceAPI::class.java)

const val NORMAL_NAMESPACE = "UNAUTHENTICATED_NAMESPACE"


package jp.zuikou.system.redditprojectsample1.di

import android.widget.ImageView
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

fun injectLoggedInFeatures() = loadLoggedFeature

private val loadLoggedFeature by lazy {
    /*loadKoinModules(
        listOf(viewLoggedModelModule,
            useCaseLoggedModule,
            repositoryLoggedModule,
            dataSourceLoggedModule,
            apiLoggedModule)
    )*/
}

val viewLoggedModelModule: Module = module {
    /*viewModel { HealthCareExerciseViewModel( get()) }*/
}

val useCaseLoggedModule: Module = module {
    //factory { LoginUseCase(loginRepository = get()) }
}

val repositoryLoggedModule: Module = module {
    /*factory { ExerciseRepositoryImpl(exerciseCallAPI = get()) as ExerciseRepository }*/
}

val dataSourceLoggedModule: Module = module {
    //factory { LoginDatasourceImpl(loginService = get()) as LoginDatasource }

}




val apiLoggedModule: Module = module {
    single<Retrofit>(named(LOGGED_NAMESPACE)) {createNetworkClient()}
    single<PostsServiceAPI> { provideExerciseCallService(get(qualifier = named(LOGGED_NAMESPACE)))}
}


private fun provideExerciseCallService(retrofit: Retrofit): PostsServiceAPI = retrofit.create(PostsServiceAPI::class.java)

const val LOGGED_NAMESPACE = "AUTHENTICATED_NAMESPACE"
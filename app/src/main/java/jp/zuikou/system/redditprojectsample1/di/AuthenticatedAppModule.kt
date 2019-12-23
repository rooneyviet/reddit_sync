package jp.zuikou.system.redditprojectsample1.di

import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
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
    single<Retrofit>(named(LOGGED_NAMESPACE)) { createNetworkClientInsurance() }
    single<PostsServiceAPI> { provideExerciseCallService(get(qualifier = named(LOGGED_NAMESPACE)))}
}


private fun provideExerciseCallService(retrofit: Retrofit): PostsServiceAPI = retrofit.create(PostsServiceAPI::class.java)

const val LOGGED_NAMESPACE = "AUTHENTICATED_NAMESPACE"
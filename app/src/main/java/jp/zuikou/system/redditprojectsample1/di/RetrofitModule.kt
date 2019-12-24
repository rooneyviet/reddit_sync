package jp.zuikou.system.redditprojectsample1.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import jp.zuikou.system.redditprojectsample1.BuildConfig
import jp.zuikou.system.redditprojectsample1.BuildConfig.DEBUG
import jp.zuikou.system.redditprojectsample1.config.AppConfig
import jp.zuikou.system.redditprojectsample1.data.service.PostsServiceAPI
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.API_REQUEST_HEADER_AUTHEN_INTERCEPTOR
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.API_REQUEST_HEADER_UNAUTHEN_INTERCEPTOR
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.HTTP_LOG_INTERCEPTOR
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.OKHTTP_CLIENT_AUTHEN_NAMESPACE
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.OKHTTP_CLIENT_UNAUTHEN_NAMESPACE
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.RETROFIT_CHOOSE_NAMESPACE
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.RETROFIT_LOGGED_NAMESPACE
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject.RETROFIT_NOT_LOGGED_NAMESPACE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitObject {

    const val HTTP_LOG_INTERCEPTOR = "HTTP_LOG_INTERCEPTOR"
    const val API_REQUEST_HEADER_AUTHEN_INTERCEPTOR: String =
        "API_REQUEST_HEADER_AUTHEN_INTERCEPTOR"
    const val API_REQUEST_HEADER_UNAUTHEN_INTERCEPTOR: String =
        "API_REQUEST_HEADER_UNAUTHEN_INTERCEPTOR"

    const val OKHTTP_CLIENT_AUTHEN_NAMESPACE: String =
        "OKHTTP_CLIENT_AUTHEN_NAMESPACE"
    const val OKHTTP_CLIENT_UNAUTHEN_NAMESPACE: String =
        "OKHTTP_CLIENT_UNAUTHEN_NAMESPACE"

    const val RETROFIT_LOGGED_NAMESPACE: String =
        "RETROFIT_LOGGED_NAMESPACE"
    const val RETROFIT_NOT_LOGGED_NAMESPACE: String =
        "RETROFIT_NOT_LOGGED_NAMESPACE"

    const val RETROFIT_CHOOSE_NAMESPACE: String =
        "RETROFIT_CHOOSE_NAMESPACE"
}

val retrofitModule = module {
    single<RxJava2CallAdapterFactory> { RxJava2CallAdapterFactory.create() }

    single<GsonConverterFactory> { GsonConverterFactory.create(get()) }

    single<Gson> { GsonBuilder().create() }

    single<OkHttpClient>(named(OKHTTP_CLIENT_AUTHEN_NAMESPACE)) {
        OkHttpClient.Builder()
            .addInterceptor(get(named(HTTP_LOG_INTERCEPTOR)))
            .addInterceptor(get(named(API_REQUEST_HEADER_AUTHEN_INTERCEPTOR)))
            .build()

    }

    single<OkHttpClient>(named(OKHTTP_CLIENT_UNAUTHEN_NAMESPACE)) {
        OkHttpClient.Builder()
            .addInterceptor(get(named(HTTP_LOG_INTERCEPTOR)))
            .addInterceptor(get(named(API_REQUEST_HEADER_UNAUTHEN_INTERCEPTOR)))
            .build()
    }

    single<Interceptor>(named(HTTP_LOG_INTERCEPTOR)) {
        val logger = HttpLoggingInterceptor()
        logger.level = if (DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        logger
    }

    factory<Interceptor>(named(API_REQUEST_HEADER_AUTHEN_INTERCEPTOR)) {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "bearer 112931652197-JYNPD1cS3G77_KoJxAP0TmUHf5E")
                    .build()
            )
        }
    }

    factory<Interceptor>(named(API_REQUEST_HEADER_UNAUTHEN_INTERCEPTOR)) {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .build()
            )
        }
    }

    single<Retrofit>(named(RETROFIT_LOGGED_NAMESPACE)) {
        Retrofit.Builder()
            .client(get(named(OKHTTP_CLIENT_AUTHEN_NAMESPACE)))
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .baseUrl(AppConfig.AUTHENTICATED_BASE_URL)
            .build()
    }

    single<Retrofit>(named(RETROFIT_NOT_LOGGED_NAMESPACE)) {
        Retrofit.Builder()
            .client(get(named(OKHTTP_CLIENT_UNAUTHEN_NAMESPACE)))
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .baseUrl(AppConfig.UNAUTHENTICATED_BASE_URL)
            .build()
    }


    single<PostsServiceAPI> {
        providePostService(
            get<Retrofit>(
                named(
                    getProperty(
                        RETROFIT_CHOOSE_NAMESPACE,
                        RETROFIT_NOT_LOGGED_NAMESPACE
                    )
                )
            )
        )
    }

}

private fun providePostService(retrofit: Retrofit): PostsServiceAPI =
    retrofit.create(PostsServiceAPI::class.java)

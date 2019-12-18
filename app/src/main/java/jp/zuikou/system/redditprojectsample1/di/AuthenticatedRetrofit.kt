package jp.zuikou.system.redditprojectsample1.di

import com.google.gson.GsonBuilder
import jp.zuikou.system.redditprojectsample1.config.AppConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClientInsurance(debug: Boolean = false) =
    retrofitClient(httpClient(debug))

private fun httpClient(debug: Boolean): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    clientBuilder.addInterceptor {
        it.proceed(
            it.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Accept", "application/json")
                //.addHeader("Authorization", SharedPreferenceSingleton.getBearerTokenUUiD())
                .build()
        )
    }


    clientBuilder.addInterceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            //.addQueryParameter("X-Zuikou-Device-Token", "92f3fd8101d1d3a3613339d37c0452b9")
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
    return clientBuilder.build()
}

private val gson = GsonBuilder().create()

private fun retrofitClient(httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(AppConfig.AUTHENTICATED_BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
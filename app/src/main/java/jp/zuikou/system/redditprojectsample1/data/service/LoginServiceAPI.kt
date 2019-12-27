package jp.zuikou.system.redditprojectsample1.data.service

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.config.AppConfig
import jp.zuikou.system.redditprojectsample1.data.model.response.login.JsonAccessTokenResponse
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import okhttp3.Credentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginServiceAPI {
    @POST("api/v1/access_token")
    @FormUrlEncoded
    fun getAccessToken(@Field("code") code: String?,
                       @Header("Authorization") basicAuthentication:String = Credentials.basic(AppConfig.CLIENT_ID,""),
                       @Field("grant_type") grantType: String = "authorization_code",
                       @Field("redirect_uri") redirectUri: String = AppConfig.REDIRECT_URI): Single<JsonAccessTokenResponse>


    @POST("api/v1/access_token")
    @FormUrlEncoded
    fun refreshingTheToken(@Header("Authorization") basicAuthentication:String = Credentials.basic(AppConfig.CLIENT_ID,""),
                           @Field("grant_type") grantType: String = "refresh_token",
                           @Field("refresh_token") refreshToken: String? = SharedPreferenceSingleton.getAccessTokenEntity()?.refreshToken): Single<JsonAccessTokenResponse>
}
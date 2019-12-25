package jp.zuikou.system.redditprojectsample1.data.service

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.config.AppConfig
import jp.zuikou.system.redditprojectsample1.data.model.response.login.JsonAccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginServiceAPI {
    @POST("api/v1/access_token")
    @FormUrlEncoded
    fun getAccessToken(@Field("code") code: String?,
                       @Header("Authorization") basicAuthentication:String? = null,
                       @Field("grant_type") grantType: String = "authorization_code",
                       @Field("redirect_uri") redirectUri: String = AppConfig.REDIRECT_URI): Single<JsonAccessTokenResponse>
}
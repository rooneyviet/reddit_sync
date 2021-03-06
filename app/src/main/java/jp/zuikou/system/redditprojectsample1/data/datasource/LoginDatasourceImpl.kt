package jp.zuikou.system.redditprojectsample1.data.datasource

import android.util.Base64
import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.config.AppConfig.CLIENT_ID
import jp.zuikou.system.redditprojectsample1.data.mapper.JsonAccessTokenMapper
import jp.zuikou.system.redditprojectsample1.data.service.LoginServiceAPI
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity
import okhttp3.Credentials


class LoginDatasourceImpl(private val loginServiceAPI: LoginServiceAPI): LoginDatasource {
    override fun getAccessToken(code: String?): Single<AccessTokenEntity> {
        val basicAuth = Credentials.basic(CLIENT_ID,"")
        val authString = "$CLIENT_ID:"
        val encodedAuthString = "Basic "+ Base64.encodeToString(
            authString.toByteArray(),
            Base64.NO_WRAP
        )
        return loginServiceAPI.getAccessToken(code, basicAuth)
            .map {
                //SharedPreferenceSingleton.setAccessTokenEntity(JsonAccessTokenMapper.transformTo(it))
                JsonAccessTokenMapper.transformTo(it)
            }

    }
}
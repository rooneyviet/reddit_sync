package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.login.JsonAccessTokenResponse
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity
import org.joda.time.DateTime

object JsonAccessTokenMapper : Mapper<JsonAccessTokenResponse, AccessTokenEntity>() {
    override fun transformFrom(source: AccessTokenEntity): JsonAccessTokenResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonAccessTokenResponse): AccessTokenEntity {
        val expiredInDateTime = DateTime.now().plusSeconds(source.expiresIn)

        return AccessTokenEntity(
            accessToken = source.accessToken,
            expiresIn = expiredInDateTime,
            refreshToken = source.refreshToken
        )
    }
}
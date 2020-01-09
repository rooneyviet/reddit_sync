package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.kintaiapp.presentation.extensions.DateFormat
import jp.zuikou.system.kintaiapp.presentation.extensions.convertLocalDateTimeJodaToString
import jp.zuikou.system.redditprojectsample1.data.model.response.login.JsonAccessTokenResponse
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

object JsonAccessTokenMapper : Mapper<JsonAccessTokenResponse, AccessTokenEntity>() {
    override fun transformFrom(source: AccessTokenEntity): JsonAccessTokenResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonAccessTokenResponse): AccessTokenEntity {
        val expiredInDateTime = LocalDateTime.now().plusSeconds(source.expiresIn)

        return AccessTokenEntity(
            accessToken = source.accessToken,
            expiresIn = expiredInDateTime.convertLocalDateTimeJodaToString(DateFormat.FULL_LONG_DATE_FORMAT_NOSPACE_NOCOLON),
            refreshToken = source.refreshToken
        )
    }
}
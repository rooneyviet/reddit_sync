package jp.zuikou.system.redditprojectsample1.domain.model

import org.joda.time.DateTime

data class AccessTokenEntity(
    val accessToken: String,
    val expiresIn: DateTime,
    val refreshToken: String
)
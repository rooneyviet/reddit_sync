package jp.zuikou.system.redditprojectsample1.domain.model

import jp.zuikou.system.kintaiapp.presentation.extensions.DateFormat
import jp.zuikou.system.kintaiapp.presentation.extensions.convertStringToLocalDateTimeJoda
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

data class AccessTokenEntity(
    var accessToken: String? = null,
    var expiresIn: String,
    var refreshToken: String? = null
){

    fun getExpiresInLocalDateTime(): LocalDateTime{
        return expiresIn.convertStringToLocalDateTimeJoda(DateFormat.FULL_LONG_DATE_FORMAT_NOSPACE_NOCOLON)
    }


}
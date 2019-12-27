package jp.zuikou.system.kintaiapp.presentation.extensions

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat


object DateFormat {
    const val YEARMONTHDAY = "yyyy年MM月dd日"
    const val YEARMONTHDAYWEEKENDDAY = "yyyy年MM月dd日 E曜日"
    const val WEEKDAY_MONTH_DAY = "EEE, MMM dd"
    const val FULL_LONG_DATE_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZZ"
    const val FULL_LONG_DATE_FORMAT_NOSPACE_NOCOLON = "yyyyMMddHHmmss"
    const val FULL_LONG_DATE_FORMAT_JAPANESE = "yyyy年MM月dd日 HH:mm"
    const val HOUR_MINUTES = "H:mm"
}

fun DateTime.convertDateTimeJodaToString(dateFormat: String): String =
    DateTimeFormat.forPattern(dateFormat).print(this)

fun LocalDateTime.convertLocalDateTimeJodaToString(dateFormat: String): String =
    DateTimeFormat.forPattern(dateFormat).print(this)

fun String?.convertStringToLocalDateTimeJoda(dateFormatPattern: String): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormat.forPattern(dateFormatPattern))


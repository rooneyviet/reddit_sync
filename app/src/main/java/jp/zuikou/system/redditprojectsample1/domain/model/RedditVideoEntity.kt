package jp.zuikou.system.redditprojectsample1.domain.model

data class RedditVideoEntity (
    val dashUrl: String?,
    val duration: Int?,
    val fallbackUrl: String?,
    val height: Int?,
    val hlsUrl: String?,
    val isGif: Boolean = false,
    val scrubberMediaUrl: String?,
    val transcodingStatus: String?,
    val width: Int?
)




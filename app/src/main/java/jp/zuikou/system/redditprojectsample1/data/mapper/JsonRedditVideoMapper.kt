package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.JsonImage
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonRedditVideo
import jp.zuikou.system.redditprojectsample1.domain.model.RedditVideoEntity

object JsonRedditVideoMapper: Mapper<JsonRedditVideo, RedditVideoEntity>() {
    override fun transformFrom(source: RedditVideoEntity): JsonRedditVideo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonRedditVideo): RedditVideoEntity =
        RedditVideoEntity(
            source.dashUrl,
            source.duration,
            source.fallbackUrl,
            source.height,
            source.hlsUrl,
            source.isGif,
            source.scrubberMediaUrl,
            source.transcodingStatus,
            source.width
            )

    fun transformFromGifMp4ToRedditVideo(source: JsonImage): RedditVideoEntity =
        RedditVideoEntity(
            dashUrl =  null,
            duration = null,
            fallbackUrl = source.url,
            height = source.height,
            hlsUrl = null,
            isGif = true,
            scrubberMediaUrl = null,
            transcodingStatus = null,
            width = source.width
        )

}
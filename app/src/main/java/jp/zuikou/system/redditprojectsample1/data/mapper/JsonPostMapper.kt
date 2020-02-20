package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPostResponse
import jp.zuikou.system.redditprojectsample1.domain.model.ImageEntity
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import java.util.*

object JsonPostMapper : Mapper<JsonPostResponse, PostEntity>() {
    override fun transformFrom(source: PostEntity): JsonPostResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonPostResponse): PostEntity {
        var mIsGif = false

        val imagePreview = mutableListOf<ImageEntity>()
        source.preview?.images?.forEach { content ->
            content.image?.let { image ->
                if (image.url.isNullOrEmpty()) return@let
                imagePreview.add(JsonImageMapper.transformTo(image))
            }
            content.resolutions?.let { images ->
                imagePreview.addAll(JsonImageMapper.transformToList(images))
            }
        }
        /*source.preview?.images?.firstOrNull()?.variants?.gif?.let {
            imagePreview.clear()
            mIsGif = true
            it.source?.let { image ->
                if (image.url.isNullOrEmpty()) return@let
                imagePreview.add(JsonImageMapper.transformTo(image))
            }
            it.resolutions?.let { images ->
                imagePreview.addAll(JsonImageMapper.transformToList(images))
            }
        }*/
        val date = if (source.createdUtc == null) Date() else Date(source.createdUtc * 1000)

        val mRedditVideo = if(source.media?.jsonRedditVideo!=null){
             JsonRedditVideoMapper.transformTo(source.media.jsonRedditVideo)
        } else if(source.preview?.images?.firstOrNull()?.variants?.gif?.source != null){
            mIsGif = true
             JsonRedditVideoMapper.transformFromGifMp4ToRedditVideo(source.preview?.images?.firstOrNull()?.variants?.gif?.source!!)
        } else null
        /*val mRedditVideo = source.media?.jsonRedditVideo?.let {
            JsonRedditVideoMapper.transformTo(it)
        } ?: run{
            source.preview?.images?.firstOrNull()?.variants?.gif?.let {
                mIsGif = true
                it.source?.let { image ->
                    if (image.url.isNullOrEmpty()) return@let
                    JsonRedditVideoMapper.transformFromGifMp4ToRedditVideo(it.source)
                }?: null
            }
        }*/

        /* return Post(title = source.title ?: "", imagePreview = imagePreview,
             author = Author(name = source.authorName ?: ""), date = date, text = source.text ?: "",
             remoteId = source.id ?: "", link = source.link ?: "")*/

        return PostEntity(
            allAwardings = JsonAllAwardMapper.transformToList(source.allAwardings),
            allowLiveComments = source.allowLiveComments,
            approvedAtUtc = source.approvedAtUtc,
            archived = source.archived,
            author = source.author,
            authorFullname = source.authorFullname,
            canGild = source.canGild,
            canModPost = source.canModPost,
            category = source.category,
            clicked = source.clicked,
            contentCategories = source.contentCategories,
            contestMode = source.contestMode,
            created = date,
            createdUtc = date,
            discussionType = source.discussionType,
            distinguished = source.distinguished,
            domain = source.domain,
            downs = source.downs,
            /*edited = source.edited,*/
            gilded = source.gilded,
            hidden = source.hidden,
            hideScore = source.hideScore,
            remoteId = source.id,
            isCrosspostable = source.isCrosspostable,
            isMeta = source.isMeta,
            isOriginalContent = source.isOriginalContent,
            isRedditMediaDomain = source.isRedditMediaDomain,
            isRobotIndexable = source.isRobotIndexable,
            isSelf = source.isSelf,
            isVideo = source.isVideo,
            likes = source.likes,
            locked = source.locked,
            mediaOnly = source.mediaOnly,
            name = source.name,
            noFollow = source.noFollow,
            numComments = source.numComments,
            numCrossposts = source.numCrossposts,
            numReports = source.numReports,
            over18 = source.over18,
            parentWhitelistStatus = source.parentWhitelistStatus,
            permalink = source.permalink,
            pinned = source.pinned,
            pwls = source.pwls,
            quarantine = source.quarantine,
            removalReason = source.removalReason,
            reportReasons = source.reportReasons,
            saved = source.saved,
            score = source.score,
            selftext = source.selftext,
            selftextHtml = source.selftextHtml,
            sendReplies = source.sendReplies,
            spoiler = source.spoiler,
            stickied = source.stickied,
            subreddit = source.subreddit,
            subredditId = source.subredditId,
            subredditNamePrefixed = source.subredditNamePrefixed,
            subredditSubscribers = source.subredditSubscribers,
            subredditType = source.subredditType,
            suggestedSort = source.suggestedSort,
            thumbnail = source.thumbnail,
            title = source.title,
            totalAwardsReceived = source.totalAwardsReceived,
            ups = source.ups,
            url = source.url,
            viewCount = source.viewCount,
            visited = source.visited,
            whitelistStatus = source.parentWhitelistStatus,
            wls = source.wls,
            imagePreview = imagePreview,
            redditVideoEntity = mRedditVideo,
            isGif = mIsGif
        )
    }
}
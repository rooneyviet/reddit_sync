package jp.zuikou.system.redditprojectsample1.data.model.response

import com.google.gson.annotations.SerializedName

data class JsonPreview(val images: List<JsonContentImage> = listOf(),
                       @SerializedName("reddit_video_preview")
                       val redditVideoPreview: JsonRedditVideo? = null)
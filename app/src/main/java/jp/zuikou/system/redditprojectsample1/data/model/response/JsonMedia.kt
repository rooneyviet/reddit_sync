package jp.zuikou.system.redditprojectsample1.data.model.response


import com.google.gson.annotations.SerializedName

data class JsonMedia(
    @SerializedName("reddit_video")
    val jsonRedditVideo: JsonRedditVideo?
)
package jp.zuikou.system.redditprojectsample1.data.model.response


import com.google.gson.annotations.SerializedName

data class ResizedIcon(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)
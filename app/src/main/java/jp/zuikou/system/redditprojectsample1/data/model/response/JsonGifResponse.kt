package jp.zuikou.system.redditprojectsample1.data.model.response


import com.google.gson.annotations.SerializedName

data class JsonGifResponse(
    @SerializedName("resolutions")
    val resolutions: List<JsonImage>?,
    @SerializedName("source")
    val source: JsonImage?
)
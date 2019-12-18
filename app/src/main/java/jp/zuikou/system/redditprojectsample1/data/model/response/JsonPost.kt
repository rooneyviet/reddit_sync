package jp.zuikou.system.redditprojectsample1.data.model.response

import com.google.gson.annotations.SerializedName

data class JsonPost(@SerializedName("title")
                    val title: String? = "",
                    @SerializedName("selftext")
                    val text: String? = "",
                    @SerializedName("preview")
                    val preview: JsonPreview? = JsonPreview(),
                    @SerializedName("author")
                    val authorName: String? = "",
                    @SerializedName("created_utc")
                    val createdUtc: Long? = 0L,
                    @SerializedName("id")
                    val id: String? = "",
                    @SerializedName("link")
                    val link: String? = "")
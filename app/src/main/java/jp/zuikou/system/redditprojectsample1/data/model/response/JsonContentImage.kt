package jp.zuikou.system.redditprojectsample1.data.model.response

import com.google.gson.annotations.SerializedName

data class JsonContentImage(@SerializedName("source")
                            val image: JsonImage? = JsonImage(),
                            @SerializedName("resolutions")
                            val resolutions: List<JsonImage>? = listOf(),
                            @SerializedName("variants")
                            val variants: JsonVariantsResponse? = null)
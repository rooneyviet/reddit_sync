package jp.zuikou.system.redditprojectsample1.data.model.response

import com.google.gson.annotations.SerializedName

data class JsonVariantsResponse(@SerializedName("gif")
                                val gif: JsonGifResponse?) {
}
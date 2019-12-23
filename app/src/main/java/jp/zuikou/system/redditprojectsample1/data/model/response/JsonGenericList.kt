package jp.zuikou.system.redditprojectsample1.data.model.response

import com.google.gson.annotations.SerializedName

data class JsonGenericList<T>(val children: List<JsonGenericResponseWrapper<T>>? = listOf(),
                              @SerializedName("after") val after: String? = null)
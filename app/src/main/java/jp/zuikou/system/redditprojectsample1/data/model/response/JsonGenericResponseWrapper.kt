package jp.zuikou.system.redditprojectsample1.data.model.response

data class JsonGenericResponseWrapper<T>(private val kind: String? = "", val data: T? = null)
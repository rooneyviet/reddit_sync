package jp.zuikou.system.redditprojectsample1.domain.requestvalue

data class GetPostsRequestValue(val subReddit: String? = null,
                                 val type: String? = null,
                                 val page: String): RequestValues
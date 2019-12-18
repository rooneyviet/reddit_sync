package jp.zuikou.system.redditprojectsample1.data.service

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericList
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericResponseWrapper
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPost
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPostResponse
import retrofit2.http.*

interface PostsServiceAPI {
    @GET("{subreddit}/hot.json")
    fun getPagedListPosts(@Path("subreddit", encoded = true) subReddit: String? = null,
                          @Query("after") nextPage: String = ""): Single<JsonGenericResponseWrapper<JsonGenericList<JsonPostResponse>>>
}
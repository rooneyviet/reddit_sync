package jp.zuikou.system.redditprojectsample1.data.service

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericList
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericResponseWrapper
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPost
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPostResponse
import jp.zuikou.system.redditprojectsample1.data.model.response.subcribers.JsonSubcribersResponse
import retrofit2.http.*

interface PostsServiceAPI {
    @GET("{subreddit}/{type}.json?raw_json=1")
    fun getPagedListPosts(@Path("subreddit", encoded = true) subReddit: String? = null,
                          @Path("type") type: String? = null,
                          @Query("after") nextPage: String = ""): Single<JsonGenericResponseWrapper<JsonGenericList<JsonPostResponse>>>

    @GET("subreddits/mine/subscribers")
    fun getPagedListMineSubscribers(
                          @Query("limit") limit: Int = 100): Single<JsonGenericResponseWrapper<JsonGenericList<JsonSubcribersResponse>>>
}
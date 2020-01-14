package jp.zuikou.system.redditprojectsample1.data.service

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericList
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonGenericResponseWrapper
import jp.zuikou.system.redditprojectsample1.data.model.response.JsonPostResponse
import jp.zuikou.system.redditprojectsample1.data.model.response.subcribers.JsonSubcribersResponse
import retrofit2.http.*

interface PostsServiceAPI {
    @GET("{subreddit}/{type}.json?raw_json=1")
    fun getPagedListPosts(@Path("subreddit", encoded = true) subReddit: String? = null,
                          @Path("type") type: String? = null,
                          @Query("after") nextPage: String? = null): Single<JsonGenericResponseWrapper<JsonGenericList<JsonPostResponse>>>

    @GET("subreddits/mine/subscriber")
    fun getPagedListMineSubscribers(@Query("after") nextPage: String? = null,
                          @Query("limit") limit: Int? = 100): Single<JsonGenericResponseWrapper<JsonGenericList<JsonSubcribersResponse>>>


    @POST("api/vote")
    @FormUrlEncoded
    fun votePost(@Field("dir") isUpvote: Int,
                 @Field("id") postId: String): Single<Void>
}
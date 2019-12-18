package jp.zuikou.system.redditprojectsample1.data.model.response


import com.google.gson.annotations.SerializedName

data class JsonPostResponse(
    @SerializedName("all_awardings")
    val allAwardings: List<AllAwarding> = listOf(),
    @SerializedName("allow_live_comments")
    val allowLiveComments: Boolean?,
    @SerializedName("approved_at_utc")
    val approvedAtUtc: Long?,
    @SerializedName("archived")
    val archived: Boolean?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("author_fullname")
    val authorFullname: String?,
    @SerializedName("can_gild")
    val canGild: Boolean?,
    @SerializedName("can_mod_post")
    val canModPost: Boolean?,
    @SerializedName("category")
    val category: Any?,
    @SerializedName("clicked")
    val clicked: Boolean?,
    @SerializedName("content_categories")
    val contentCategories: Any?,
    @SerializedName("contest_mode")
    val contestMode: Boolean?,
    @SerializedName("created")
    val created: Double?,
    @SerializedName("created_utc")
    val createdUtc: Long?,
    @SerializedName("discussion_type")
    val discussionType: Any?,
    @SerializedName("distinguished")
    val distinguished: String?,
    @SerializedName("domain")
    val domain: String?,
    @SerializedName("downs")
    val downs: Int?,
    /*@SerializedName("edited")
    val edited: Int?,*/
    @SerializedName("gilded")
    val gilded: Int?,
    @SerializedName("hidden")
    val hidden: Boolean?,
    @SerializedName("hide_score")
    val hideScore: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("is_crosspostable")
    val isCrosspostable: Boolean?,
    @SerializedName("is_meta")
    val isMeta: Boolean?,
    @SerializedName("is_original_content")
    val isOriginalContent: Boolean?,
    @SerializedName("is_reddit_media_domain")
    val isRedditMediaDomain: Boolean?,
    @SerializedName("is_robot_indexable")
    val isRobotIndexable: Boolean?,
    @SerializedName("is_self")
    val isSelf: Boolean?,
    @SerializedName("is_video")
    val isVideo: Boolean?,
    @SerializedName("likes")
    val likes: Any?,
    @SerializedName("locked")
    val locked: Boolean?,
    @SerializedName("media")
    val media: Any?,
    @SerializedName("media_only")
    val mediaOnly: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("no_follow")
    val noFollow: Boolean?,
    @SerializedName("num_comments")
    val numComments: Int?,
    @SerializedName("num_crossposts")
    val numCrossposts: Int?,
    @SerializedName("num_reports")
    val numReports: Any?,
    @SerializedName("over_18")
    val over18: Boolean?,
    @SerializedName("parent_whitelist_status")
    val parentWhitelistStatus: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("pinned")
    val pinned: Boolean?,
    @SerializedName("pwls")
    val pwls: Int?,
    @SerializedName("quarantine")
    val quarantine: Boolean?,
    @SerializedName("removal_reason")
    val removalReason: Any?,
    @SerializedName("report_reasons")
    val reportReasons: Any?,
    @SerializedName("saved")
    val saved: Boolean?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("selftext")
    val selftext: String?,
    @SerializedName("selftext_html")
    val selftextHtml: String?,
    @SerializedName("send_replies")
    val sendReplies: Boolean?,
    @SerializedName("spoiler")
    val spoiler: Boolean?,
    @SerializedName("stickied")
    val stickied: Boolean?,
    @SerializedName("subreddit")
    val subreddit: String?,
    @SerializedName("subreddit_id")
    val subredditId: String?,
    @SerializedName("subreddit_name_prefixed")
    val subredditNamePrefixed: String?,
    @SerializedName("subreddit_subscribers")
    val subredditSubscribers: Int?,
    @SerializedName("subreddit_type")
    val subredditType: String?,
    @SerializedName("suggested_sort")
    val suggestedSort: Any?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total_awards_received")
    val totalAwardsReceived: Int?,
    @SerializedName("ups")
    val ups: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("view_count")
    val viewCount: Any?,
    @SerializedName("visited")
    val visited: Boolean?,
    @SerializedName("whitelist_status")
    val whitelistStatus: String?,
    @SerializedName("wls")
    val wls: Int?,
    @SerializedName("preview")
    val preview: JsonPreview? = JsonPreview()
)
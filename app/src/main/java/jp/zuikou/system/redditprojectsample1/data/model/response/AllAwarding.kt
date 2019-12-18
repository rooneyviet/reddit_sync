package jp.zuikou.system.redditprojectsample1.data.model.response


import com.google.gson.annotations.SerializedName

data class AllAwarding(
    @SerializedName("award_type")
    val awardType: String?,
    @SerializedName("coin_price")
    val coinPrice: Int?,
    @SerializedName("coin_reward")
    val coinReward: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("days_of_drip_extension")
    val daysOfDripExtension: Int?,
    @SerializedName("days_of_premium")
    val daysOfPremium: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("end_date")
    val endDate: Int?,
    @SerializedName("icon_height")
    val iconHeight: Int?,
    @SerializedName("icon_url")
    val iconUrl: String?,
    @SerializedName("icon_width")
    val iconWidth: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("is_enabled")
    val isEnabled: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("resized_icons")
    val resizedIcons: List<ResizedIcon?>?,
    @SerializedName("start_date")
    val startDate: Int?,
    @SerializedName("subreddit_coin_reward")
    val subredditCoinReward: Int?,
    @SerializedName("subreddit_id")
    val subredditId: String?
)
package jp.zuikou.system.redditprojectsample1.data.model.response.login


import com.google.gson.annotations.SerializedName

data class JsonAccessTokenResponse (
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)
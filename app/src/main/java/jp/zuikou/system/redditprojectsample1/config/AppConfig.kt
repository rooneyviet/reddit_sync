package jp.zuikou.system.redditprojectsample1.config

object AppConfig {
    const val UNAUTHENTICATED_BASE_URL = "https://ssl.reddit.com/"
    const val AUTHENTICATED_BASE_URL = "https://oauth.reddit.com/"


    const val AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
            "&response_type=code&state=%s&redirect_uri=%s&" +
            "duration=permanent&scope=%s&"


    const val CLIENT_ID = "kB1jsU4kMBQbcA"

    const val REDIRECT_URI = "http://redditquan/auth"

    const val STATE = "MY_RANDOM_STRING_1"

    const val SCOPE = "account,edit,flair,history,identity,modconfig,modcontributors,modflair,modlog,modposts,modwiki,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,wikiedit,wikiread"
}
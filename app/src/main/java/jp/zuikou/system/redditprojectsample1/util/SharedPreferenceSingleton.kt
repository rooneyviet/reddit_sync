package jp.zuikou.system.redditprojectsample1.util

import android.content.Context
import android.content.SharedPreferences
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject

object SharedPreferenceSingleton {
    lateinit var pref: SharedPreferences

    fun init(context: Context) {
        val key = "preference"
        pref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }


    @JvmStatic
    fun setAccessToken(accessToken: String?) {
        pref.edit().apply {
            putString(Key.ACCESS_TOKEN, accessToken)
        }.apply()
    }

    fun getAccessToken(): String? =
        pref.getString(Key.ACCESS_TOKEN, null)

    fun isAccessTokenSaved(): Boolean =
        getAccessToken() != null

    fun getRetrofitNameSpace(): String = if (isAccessTokenSaved()) RetrofitObject.RETROFIT_LOGGED_NAMESPACE else RetrofitObject.RETROFIT_NOT_LOGGED_NAMESPACE


    @JvmStatic
    fun setRecipeTitleIdHashMapString(recipeTitleIdMapString: String?) {
        pref.edit().apply {
            putString(Key.RECIPE_TITLE_ID_HASHMAP, recipeTitleIdMapString)
        }.apply()
    }


    fun getBearerTokenUUiD(): String? =
        "Bearer ${getAccessToken()}"

    @JvmStatic
    fun setCurrentThemePref(currentTheme: String?) {
        pref.edit().apply {
            putString(Key.THEME_PREF, currentTheme)
        }.apply()
    }

    fun getCurrentThemePref(): String? =
        pref.getString(Key.THEME_PREF, ThemeHelper.DEFAULT_MODE)





    private object Key {
        const val ACCESS_TOKEN = "access_token"
        const val THEME_PREF = "theme_pref"
        const val RECIPE_TITLE_ID_HASHMAP = "RECIPE_TITLE_ID_HASHMAP"
        const val BEARER_TOKEN = "BEARER_TOKEN"
    }
}
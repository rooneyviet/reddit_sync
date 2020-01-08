package jp.zuikou.system.redditprojectsample1.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity

object SharedPreferenceSingleton {
    lateinit var pref: SharedPreferences

    fun init(context: Context) {
        val key = "preference"
        pref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }


    /*@JvmStatic
    fun setAccessToken(accessToken: String?) {
        pref.edit().apply {
            putString(Key.ACCESS_TOKEN, accessToken)
        }.apply()
    }

    fun getAccessToken(): String? =
        pref.getString(Key.ACCESS_TOKEN, null)*/

    fun isAccessTokenSaved(): Boolean =
        getAccessTokenEntity() != null

    fun getRetrofitNameSpace(): String = if (isAccessTokenSaved()) RetrofitObject.RETROFIT_LOGGED_NAMESPACE else RetrofitObject.RETROFIT_NOT_LOGGED_NAMESPACE


    @JvmStatic
    fun setRecipeTitleIdHashMapString(recipeTitleIdMapString: String?) {
        pref.edit().apply {
            putString(Key.RECIPE_TITLE_ID_HASHMAP, recipeTitleIdMapString)
        }.apply()
    }

    private fun setLoginLogout(loggedIn: Boolean) {
        pref.edit().apply {
            putBoolean(Key.IS_LOGGED_IN, loggedIn)
        }.apply()
    }


    /*fun getBearerTokenUUiD(): String? =
        "Bearer ${getAccessToken()}"*/

    @JvmStatic
    fun setCurrentThemePref(currentTheme: String?) {
        pref.edit().apply {
            putString(Key.THEME_PREF, currentTheme)
        }.apply()
    }

    fun getCurrentThemePref(): String? =
        pref.getString(Key.THEME_PREF, ThemeHelper.DEFAULT_MODE)


    fun getAccessTokenEntity(): AccessTokenEntity? {
        val gson = Gson()
        val json = pref.getString(Key.ACCESS_TOKEN_ENTITY, null)
        val obj = gson.fromJson<AccessTokenEntity>(json, AccessTokenEntity::class.java)
        return obj
    }

    fun setAccessTokenEntityNull(){
        setLoginLogout(false)
        pref.edit().apply {
            putString(Key.ACCESS_TOKEN_ENTITY, null)
        }.apply()
    }

    fun setAccessTokenEntity(accessTokenEntity: AccessTokenEntity?){
        val getCurrentAccessToken = accessTokenEntity?.let {
            setLoginLogout(true)
            getAccessTokenEntity()?: AccessTokenEntity()
        }?: kotlin.run {
            setLoginLogout(false)
            null
        }

        getCurrentAccessToken?.accessToken = accessTokenEntity?.accessToken
        getCurrentAccessToken?.expiresIn = accessTokenEntity?.expiresIn
        if (getCurrentAccessToken?.refreshToken == null || accessTokenEntity?.refreshToken != null) {
            getCurrentAccessToken?.refreshToken = accessTokenEntity?.refreshToken
        }

        val gson = Gson()
        val json = gson.toJson(getCurrentAccessToken)
        pref.edit().apply {
            putString(Key.ACCESS_TOKEN_ENTITY, json)
        }.apply()
    }


    private object Key {
        const val ACCESS_TOKEN = "access_token"
        const val ACCESS_TOKEN_ENTITY = "access_token_entity"
        const val THEME_PREF = "theme_pref"
        const val RECIPE_TITLE_ID_HASHMAP = "RECIPE_TITLE_ID_HASHMAP"
        const val BEARER_TOKEN = "BEARER_TOKEN"
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }


    private val publisher = PublishSubject.create<String>()
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }

    private val updates = publisher.doOnSubscribe {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }.doOnDispose {
        if (!publisher.hasObservers())
            pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getPreferences(): SharedPreferences {
        return pref
    }

    fun isLoggedInLivePreference(): LivePreference<Boolean> {
        return LivePreference(updates, pref, Key.IS_LOGGED_IN, false)
    }

    val compositeDisposable = CompositeDisposable()


}
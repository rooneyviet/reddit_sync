package jp.zuikou.system.redditprojectsample1.application

import android.app.Application
import jp.zuikou.system.redditprojectsample1.BuildConfig
import jp.zuikou.system.redditprojectsample1.di.injectBasicFeature
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.ThemeHelper
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber

class RedditApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceSingleton.init(applicationContext)

        val currentThemePrefString = SharedPreferenceSingleton.getCurrentThemePref()
        currentThemePrefString?.let {
            ThemeHelper.applyTheme(currentThemePrefString)
        }

        RedditApplication.startKoinInApp(this@RedditApplication)



        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        JodaTimeAndroid.init(this)
    }
    companion object{
        fun startKoinInApp(application: Application){
            startKoin {
                androidContext(application)
                androidFileProperties()
                injectBasicFeature()
                //injectLoggedInFeatures()
            }
        }
    }

}
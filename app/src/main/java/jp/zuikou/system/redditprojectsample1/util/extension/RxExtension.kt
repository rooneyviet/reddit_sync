package jp.zuikou.system.redditprojectsample1.util.extension

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.jakewharton.rxbinding2.view.RxView
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.rx.RxBus
import jp.zuikou.system.redditprojectsample1.util.rx.UnAuthenEvent
import java.util.concurrent.TimeUnit

const val DURATION_DELAY = 1000L

fun View.click(duration: Long = DURATION_DELAY) =
    RxView.clicks(this).throttleFirst(duration, TimeUnit.MILLISECONDS)

fun View.click(activity: FragmentActivity, duration: Long = DURATION_DELAY, clickSuccess: () -> Unit) =
    RxView.clicks(this)
        .throttleFirst(duration, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY))
        .subscribe {
            clickSuccess()
        }


fun View.clickOnAuthenContent(clickSuccess: () -> Unit, duration: Long = DURATION_DELAY) =
    RxView.clicks(this)
        .throttleFirst(duration, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            if(SharedPreferenceSingleton.isAccessTokenSaved()) {
                clickSuccess()
            } else {
                RxBus.send(UnAuthenEvent("HTTP_UNAUTHORIZED UNAUTHORIZED!!!"))
            }
        }


fun View.click(clickSuccess: () -> Unit, duration: Long = DURATION_DELAY) =
    RxView.clicks(this)
        .throttleFirst(duration, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            clickSuccess()
        }
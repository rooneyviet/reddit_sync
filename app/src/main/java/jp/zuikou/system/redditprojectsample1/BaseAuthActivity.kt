package jp.zuikou.system.redditprojectsample1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.LoginViewModel
import jp.zuikou.system.redditprojectsample1.util.rx.LoginLogoutChangeEvent
import jp.zuikou.system.redditprojectsample1.util.rx.RxBus
import jp.zuikou.system.redditprojectsample1.util.rx.UnAuthenEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@SuppressLint("Registered")
open abstract class BaseAuthActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listenChangeUnauthenEvent()
        listenChangeLoginLogoutEvent()
    }

    private fun listenChangeUnauthenEvent() {
        RxBus.toObservable(UnAuthenEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe(
                {
                    Toast.makeText(this, "Please Login", Toast.LENGTH_LONG).show()
                },
                {
                    Timber.e(it)
                }
            )
    }

    private fun listenChangeLoginLogoutEvent() {
        RxBus.toObservable(LoginLogoutChangeEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe(
                {
                    it.isLogin?.let {
                        refreshLoginStatus(true)
                    }
                },
                {
                    Timber.e(it)
                }
            )
    }

    abstract fun refreshLoginStatus(isLogin: Boolean?)
}
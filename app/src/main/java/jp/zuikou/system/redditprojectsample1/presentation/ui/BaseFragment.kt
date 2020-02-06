package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.LoginViewModel
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.rx.LoginLogoutChangeEvent
import jp.zuikou.system.redditprojectsample1.util.rx.RxBus
import kotlinx.android.synthetic.main.include_posts_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import timber.log.Timber

abstract class BaseFragment: Fragment() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreferenceSingleton.isLoggedInLivePreference().observe(this, Observer<Boolean> { loggedIn ->
            Timber.d("LOGSTATUS $loggedIn")
            //refreshFragment()
        })

        activity?.let {
            loginViewModel = ViewModelProviders.of(it).get(LoginViewModel::class.java)

            loginViewModel.authenticationState.observe(this, Observer { authenticationState ->
                when {
                    authenticationState == LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                        //Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                        //refreshFragment()
                    }
                    authenticationState == LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> {
                        //Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                    authenticationState == LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                        //refreshFragment()
                    }
                }
            })
        }

        listenChangeLoginLogoutEvent()

    }

    abstract fun refreshFragment(isReset: Boolean)

    open fun setInitialLoadingState(networkState: NetworkState?) {
        buttonRetry.visibility = if (networkState == NetworkState.NO_INTERNET) View.VISIBLE else View.GONE
        progressBarLoading.visibility = if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE

        recyclerView.visibility = if (networkState == NetworkState.SUCCESS) View.VISIBLE else View.GONE
    }

    private fun listenChangeLoginLogoutEvent() {
        RxBus.toObservable(LoginLogoutChangeEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe(
                {
                    it.isLogin?.let {
                        refreshFragment(true)
                    }
                },
                {
                    Timber.e(it)
                }
            )
    }
}
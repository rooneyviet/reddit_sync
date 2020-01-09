package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.LoginViewModel
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import timber.log.Timber

abstract class BaseFragment: Fragment() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreferenceSingleton.isLoggedInLivePreference().observe(this, Observer<Boolean> { loggedIn ->
            Timber.d("LOGSTATUS $loggedIn")
            refreshFragment()
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

    }

    abstract fun refreshFragment()
}
package jp.zuikou.system.redditprojectsample1.presentation.viewmodel

import jp.zuikou.system.redditprojectsample1.presentation.SingleLiveEvent
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton

class LoginViewModel: BaseViewModel() {

    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED  ,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    val authenticationState = SingleLiveEvent<AuthenticationState>()

    init {
        authenticationState.value = if(SharedPreferenceSingleton.isAccessTokenSaved()){
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun authenticate(loginSuccess: Boolean) {
        if (loginSuccess) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
        }
    }

}
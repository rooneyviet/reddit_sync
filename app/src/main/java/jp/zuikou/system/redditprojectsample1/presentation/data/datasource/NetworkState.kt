package jp.zuikou.system.redditprojectsample1.presentation.data.datasource

sealed class NetworkState {
    object LOADING : NetworkState()
    object SUCCESS : NetworkState()
    object ERROR : NetworkState()
    object NO_INTERNET : NetworkState()
}
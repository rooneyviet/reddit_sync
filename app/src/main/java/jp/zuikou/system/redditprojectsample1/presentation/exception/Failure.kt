package jp.zuikou.system.redditprojectsample1.presentation.exception

sealed class Failure {
    object Generic: Failure()
    object NoInternet: Failure()
    object RequestCanceled: Failure()
    object ConnectionTimeout: Failure()
    object EmptyList : Failure()
}
package jp.zuikou.system.redditprojectsample1.util.rx

interface RxEvent

class UnAuthEvent(val message: String? = null) : RxEvent
class NoInternetConnectionEvent(val message: String? = null) : RxEvent
class UnAuthenEvent(val message: String? = null) : RxEvent
class LoginLogoutChangeEvent(val isLogin: Boolean? = null) : RxEvent

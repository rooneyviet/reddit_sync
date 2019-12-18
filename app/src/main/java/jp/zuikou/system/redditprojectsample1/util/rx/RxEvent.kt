package jp.zuikou.system.redditprojectsample1.util.rx

interface RxEvent

class UnAuthEvent(val message: String? = null) : RxEvent
class NoInternetConnectionEvent(val message: String? = null) : RxEvent

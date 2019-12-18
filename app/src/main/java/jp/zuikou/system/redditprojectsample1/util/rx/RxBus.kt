package jp.zuikou.system.redditprojectsample1.util.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxBus {
    private val publisher = PublishSubject.create<Any>()

    fun send(event: Any) {
        publisher.onNext(event)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}
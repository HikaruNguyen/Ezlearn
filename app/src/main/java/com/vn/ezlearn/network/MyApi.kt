package com.vn.ezlearn.network

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by FRAMGIA\nguyen.duc.manh on 26/12/2017.
 */

class MyApi<T>(private val obResult: Observable<T>) {
    private var mResult: T? = null
    private var mSubscription: Subscription? = null

    fun call(requestCallBack: RequestCallBack<T>) {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = obResult
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<T>() {
                    override fun onCompleted() {
                        requestCallBack.onSuccess(mResult)
                        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
                            mSubscription!!.unsubscribe()
                        mSubscription = null
                    }

                    override fun onError(e: Throwable) {
                        requestCallBack.onError(e)
                    }

                    override fun onNext(result: T?) {
                        if (result != null) {
                            mResult = result
                        }
                    }
                })
    }


    interface RequestCallBack<in T> {
        fun onSuccess(result: T?)

        fun onError(e: Throwable)
    }

}

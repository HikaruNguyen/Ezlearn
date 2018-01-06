package com.vn.ezlearn.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by pham.quy.hai on 9/9/16.
 */

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJavaCallAdapterFactory = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*> =
            RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit))

    private class RxCallAdapterWrapper internal constructor(private val retrofit: Retrofit, private val wrapped: CallAdapter<*>) : CallAdapter<Observable<*>> {

        override fun responseType(): Type = wrapped.responseType()

        override fun <R> adapt(call: Call<R>): Observable<*> {
            return (wrapped.adapt(call) as Observable<*>)
                    .onErrorResumeNext({ throwable ->
                        throwable.printStackTrace()
                        Observable.error(asRetrofitException(throwable))
                    })
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return RetrofitException.httpError(response.raw().request().url().toString(),
                        response, retrofit)
            }
            // A network error happened
            return if (throwable is IOException) {
                RetrofitException.networkError(throwable)
            } else RetrofitException.unexpectedError(throwable)
            // We don't know what happened. We need to simply convert to an unknown error
        }
    }

    companion object {

        fun create(): CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }
}

package com.vn.ezlearn.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.vn.ezlearn.R

/**
 * Created by manhi on 11/2/2017.
 */

open class BaseViewModel : BaseObservable {
    @Bindable
    var title: String = ""
    var activity: Activity? = null
    @Bindable
    var visiableError: Int = 0

    @Bindable
    var messageError: String = ""

    internal constructor() {
        visiableError = View.GONE
    }

    internal constructor(activity: Activity, title: String) {
        this.activity = activity
        this.title = title
        visiableError = View.GONE
    }

    internal constructor(activity: Activity) {
        this.activity = activity
        visiableError = View.GONE
    }

    fun hideErrorView() {
        visiableError = View.GONE
        notifyChange()
    }

    fun setErrorNetwork() {
        visiableError = View.VISIBLE
        messageError = activity!!.getString(R.string.error_connect)
        notifyChange()
    }

    fun setErrorNodata() {
        visiableError = View.VISIBLE
        messageError = activity!!.getString(R.string.no_data)
        notifyChange()
    }

    fun setErrorMesssage(message: String) {
        visiableError = View.VISIBLE
        messageError = message
        notifyChange()
    }
}

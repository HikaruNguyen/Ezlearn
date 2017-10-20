package com.vn.ezlearn.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View

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

    fun setVisiableError(isVisable: Boolean) {
        if (isVisable) {
            visiableError = View.VISIBLE
        } else {
            visiableError = View.GONE
        }
        notifyChange()
    }

}

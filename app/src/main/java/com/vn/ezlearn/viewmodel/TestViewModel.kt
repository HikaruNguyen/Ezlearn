package com.vn.ezlearn.viewmodel

import android.app.Activity
import android.databinding.ObservableInt
import android.view.View

/**
 * Created by FRAMGIA\nguyen.duc.manh on 25/09/2017.
 */

class TestViewModel(activity: Activity, title: String) : BaseViewModel(activity, title) {
    var visiableNext: ObservableInt = ObservableInt(View.GONE)
    var visiablePre: ObservableInt = ObservableInt(View.GONE)

    fun updatePosition(position: Int, size: Int) {
        visiableNext.set(if (position + 1 >= size) View.GONE else View.VISIBLE)
        visiablePre.set(if (position <= 0) View.GONE else View.VISIBLE)
    }
}

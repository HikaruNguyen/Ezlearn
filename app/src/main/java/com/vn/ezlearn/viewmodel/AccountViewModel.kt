package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.config.UserConfig

/**
 * Created by FRAMGIA\nguyen.duc.manh on 01/12/2017.
 */

class AccountViewModel(var context: Context) : BaseObservable() {
    var tvUserName: ObservableField<String> = if (!UserConfig.getInstance(context).token.isEmpty()) {
        ObservableField(UserConfig.getInstance(context).name)
    } else {
        ObservableField(context.getString(R.string.login))
    }

    var visiableLogout: ObservableInt = if (!UserConfig.getInstance(context).token.isEmpty()) {
        ObservableInt(View.VISIBLE)
    } else {
        ObservableInt(View.GONE)
    }

    fun updateProfile() {
        tvUserName.set(if (!UserConfig.getInstance(context = context).token.isEmpty()) {
            UserConfig.getInstance(context).name
        } else {
            context.getString(R.string.login)
        })
        visiableLogout.set(if (!UserConfig.getInstance(context = context).token.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        })
    }
}

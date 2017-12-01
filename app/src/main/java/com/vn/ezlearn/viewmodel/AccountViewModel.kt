package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import com.vn.ezlearn.R
import com.vn.ezlearn.config.UserConfig

/**
 * Created by FRAMGIA\nguyen.duc.manh on 01/12/2017.
 */

class AccountViewModel(context: Context) : BaseObservable() {
    var tvUserName: ObservableField<String> = if (!UserConfig.getInstance(context).token.isEmpty()) {
        ObservableField(UserConfig.getInstance(context).name)
    } else {
        ObservableField(context.getString(R.string.login))
    }

    fun updateProfile(userName: String) {
        tvUserName.set(userName)
    }
}

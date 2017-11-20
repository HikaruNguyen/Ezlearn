package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.utils.AppUtils

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/10/2017.
 */

class MainViewModel(var context: Context) : BaseObservable() {
    var visiableTabBar: ObservableInt = ObservableInt(View.GONE)
    var email: ObservableField<String> = ObservableField()
    var name: ObservableField<String> = ObservableField()
    var visiableProfile: ObservableInt = ObservableInt()
    var wallet: ObservableField<String> = ObservableField()
    var user_package: ObservableField<String> = ObservableField()

    init {
        updateProfile()
    }

    fun updateProfile() {
        if (!UserConfig.getInstance(context).token.isEmpty()) {
            name.set(UserConfig.getInstance(context).name)
            email.set(UserConfig.getInstance(context).email)
            visiableProfile.set(View.VISIBLE)
            wallet.set(context.getString(R.string.wallet,
                    AppUtils.formatMoney(UserConfig.getInstance(context).wallet)))
            user_package.set(context.getString(R.string.service_package,
                    UserConfig.getInstance(context).user_package))
        } else {
            name.set(context.getString(R.string.login))
            visiableProfile.set(View.GONE)
        }
    }

    fun setVisiableTabBar(id: String) {
        try {
            val numId = Integer.parseInt(id)
            if (numId > 0) {
                visiableTabBar.set(View.VISIBLE)
            } else {
                visiableTabBar.set(View.GONE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            visiableTabBar.set(View.GONE)
        }

    }
}

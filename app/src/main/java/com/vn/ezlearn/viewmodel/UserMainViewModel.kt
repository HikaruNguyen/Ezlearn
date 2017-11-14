package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.TextUtils
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.models.User

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class UserMainViewModel(private var context: Context) {
    var visiableAvatar: ObservableInt = ObservableInt(View.VISIBLE)
    var tvFullName: ObservableField<String> = ObservableField("")
    var tvLevelName: ObservableField<String> = ObservableField("")
    var tvWallet: ObservableField<String> = ObservableField("")
    var tvMarkAccumulation: ObservableField<String> = ObservableField("")
    var tvFreeTimesWorking: ObservableField<String> = ObservableField("")

    fun setUserInfo(user: User) {
        if (!TextUtils.isEmpty(user.full_name)) {
            tvFullName.set(user.full_name)
        }
        if (!TextUtils.isEmpty(user.level_name)) {
            tvLevelName.set(context.getString(R.string.level, user.level_name))
        }
        if (user.mark_accumulation != null) {
            tvMarkAccumulation.set(user.mark_accumulation.toString())
        } else {
            tvMarkAccumulation.set("0")
        }
        if (user.wallet != null) {
            tvWallet.set(user.wallet.toString())
        } else {
            tvWallet.set("0")
        }
        if (user.free_times_working != null) {
            tvFreeTimesWorking.set(user.free_times_working!!.toString() + "")
        } else {
            tvFreeTimesWorking.set("0")
        }
    }
}

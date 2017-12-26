package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.TextUtils
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.models.User
import com.vn.ezlearn.utils.AppUtils

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class UserMainViewModel(private var context: Context) {
    var visiableAvatar: ObservableInt = ObservableInt(View.VISIBLE)
    var tvFullName: ObservableField<String> = ObservableField("")
    var tvLevelName: ObservableField<String> = ObservableField("")
    var tvWallet: ObservableField<String> = ObservableField("")
    var tvWalletInfo: ObservableField<String> = ObservableField("")
    var tvMarkAccumulation: ObservableField<String> = ObservableField("")
    var tvMarkAccumulationInfo: ObservableField<String> = ObservableField("")
    var tvFreeTimesWorking: ObservableField<String> = ObservableField("")
    var tvFreeTimesWorkingInfo: ObservableField<String> = ObservableField("")

    var tvUsername: ObservableField<String> = ObservableField("")
    var tvFirstName: ObservableField<String> = ObservableField("")
    var tvLastName: ObservableField<String> = ObservableField("")
    var tvLevelStudyName: ObservableField<String> = ObservableField("")

    fun setUserInfo(user: User) {
        if (!TextUtils.isEmpty(user.full_name)) {
            tvFullName.set(user.full_name)
        } else {
            tvFullName.set(context.getString(R.string.null_data))
        }

        if (!TextUtils.isEmpty(user.level_name)) {
            tvLevelName.set(context.getString(R.string.level, user.level_name))
        } else {
            tvLevelName.set(context.getString(R.string.level, context.getString(R.string.null_data)))
        }
        if (user.mark_accumulation != null) {
            tvMarkAccumulation.set(user.mark_accumulation.toString())
            tvMarkAccumulationInfo.set(context.getString(R.string.achievements_point,
                    user.mark_accumulation))
        } else {
            tvMarkAccumulation.set(context.getString(R.string.null_data))
            tvMarkAccumulationInfo.set(context.getString(R.string.null_data))
        }
        if (user.wallet != null) {
            tvWallet.set(AppUtils.formatMoney(user.wallet!!))
            tvWalletInfo.set(context.getString(R.string.balance_vnd,
                    AppUtils.formatMoney(user.wallet!!)))
        } else {
            tvWallet.set(context.getString(R.string.null_data))
            tvWalletInfo.set(context.getString(R.string.null_data))
        }
        if (user.free_times_working != null) {
            tvFreeTimesWorking.set(user.free_times_working!!.toString() + "")
            tvFreeTimesWorkingInfo.set(user.free_times_working!!.toString() + "")
        } else {
            tvFreeTimesWorking.set(context.getString(R.string.null_data))
            tvFreeTimesWorkingInfo.set(context.getString(R.string.null_data))
        }

        /*=================*/
        if (!TextUtils.isEmpty(user.username)) {
            tvUsername.set(user.username)
        } else {
            tvUsername.set(context.getString(R.string.null_data))
        }
        if (!TextUtils.isEmpty(user.first_name)) {
            tvFirstName.set(user.first_name)
        } else {
            tvFirstName.set(context.getString(R.string.null_data))
        }
        if (!TextUtils.isEmpty(user.last_name)) {
            tvLastName.set(user.last_name)
        } else {
            tvLastName.set(context.getString(R.string.null_data))
        }
    }
}

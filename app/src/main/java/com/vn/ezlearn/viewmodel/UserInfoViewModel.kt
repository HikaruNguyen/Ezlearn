package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.TextUtils
import com.vn.ezlearn.R
import com.vn.ezlearn.modelresult.UserInfoResult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class UserInfoViewModel(context: Context, userInfoData: UserInfoResult.UserInfoData?) : BaseObservable() {

    lateinit var tvWallet: ObservableField<String>
    lateinit var tvFreeTimesWorking: ObservableField<String>
    lateinit var tvMarkAccumulation: ObservableField<String>
    lateinit var tvUsername: ObservableField<String>
    lateinit var tvFirstName: ObservableField<String>
    lateinit var tvLastName: ObservableField<String>
    lateinit var tvLevelStudyName: ObservableField<String>

    init {
        init()
        if (userInfoData != null) {
            if (userInfoData.user != null) {
                if (!TextUtils.isEmpty(userInfoData.user!!.username)) {
                    tvUsername.set(userInfoData.user!!.username)
                }
                if (!TextUtils.isEmpty(userInfoData.user!!.first_name)) {
                    tvFirstName.set(userInfoData.user!!.first_name)
                }
                if (!TextUtils.isEmpty(userInfoData.user!!.last_name)) {
                    tvLastName.set(userInfoData.user!!.last_name)
                }

                if (userInfoData.user!!.mark_accumulation != null) {
                    tvMarkAccumulation.set(context.getString(R.string.achievements_point,
                            userInfoData.user!!.mark_accumulation))
                } else {
                    tvMarkAccumulation.set(context.getString(R.string.achievements_point, 0))
                }
                if (userInfoData.user!!.wallet != null) {
                    tvWallet.set(context.getString(R.string.balance_vnd, userInfoData.user!!.wallet))
                } else {
                    tvWallet.set(context.getString(R.string.balance_vnd, 0))
                }
                if (userInfoData.user!!.free_times_working != null) {
                    tvFreeTimesWorking.set(userInfoData.user!!.free_times_working!!.toString() + "")
                } else {
                    tvFreeTimesWorking.set("0")
                }
            }
        }
    }

    private fun init() {

        tvWallet = ObservableField("")
        tvFreeTimesWorking = ObservableField("")

        tvMarkAccumulation = ObservableField("")
        tvUsername = ObservableField("")
        tvFirstName = ObservableField("")
        tvLastName = ObservableField("")
        tvLevelStudyName = ObservableField("")
    }
}

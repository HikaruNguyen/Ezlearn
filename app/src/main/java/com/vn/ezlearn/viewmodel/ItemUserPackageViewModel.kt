package com.vn.ezlearn.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.TextUtils

import com.vn.ezlearn.models.UserPackage

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class ItemUserPackageViewModel(userPackage: UserPackage) : BaseObservable() {
    var tvExpiredAt: ObservableField<String> = ObservableField("")
    var tvCodeName: ObservableField<String> = ObservableField("")

    init {
        if (!TextUtils.isEmpty(userPackage.code_name)) {
                tvCodeName.set(userPackage.code_name)
            }
            if (!TextUtils.isEmpty(userPackage.expired_at)) {
                tvExpiredAt.set(userPackage.expired_at)
            }
    }
}

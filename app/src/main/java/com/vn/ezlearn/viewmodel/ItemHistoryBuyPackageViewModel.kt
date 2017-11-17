package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.TextUtils
import com.vn.ezlearn.R
import com.vn.ezlearn.models.HistoryBuyPackage
import com.vn.ezlearn.utils.AppUtils

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class ItemHistoryBuyPackageViewModel(context: Context, historyBuyPackage: HistoryBuyPackage?) : BaseObservable() {
    var description: ObservableField<String> = ObservableField("")
    var price: ObservableField<String> = ObservableField("")

    init {
        if (historyBuyPackage != null) {
            if (!TextUtils.isEmpty(historyBuyPackage.description)) {
                description.set(historyBuyPackage.description)
            }
            if (historyBuyPackage.price != null) {
                price.set(AppUtils.formatMoney(historyBuyPackage.price)
                        + context.getString(R.string.vnd))
            } else {
                price.set(context.getString(R.string.updating))
            }
        }
    }
}

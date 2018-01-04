package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import com.vn.ezlearn.R
import com.vn.ezlearn.models.HistoryPayment
import com.vn.ezlearn.utils.AppUtils

@Suppress("DEPRECATION")
/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class ItemHistoryPaymentViewModel(context: Context, historyPayment: HistoryPayment?) : BaseObservable() {
    var time: ObservableField<String> = ObservableField("")
    var channel: ObservableField<Spanned> = ObservableField()
    var description: ObservableField<Spanned> = ObservableField()
    var amount: ObservableField<Spanned> = ObservableField()

    init {
        if (historyPayment != null) {
            if (!TextUtils.isEmpty(historyPayment.created_at)) {
                time.set(historyPayment.created_at)
            } else {
                time.set(context.getString(R.string.updating))
            }

            if (!TextUtils.isEmpty(historyPayment.channel_name)) {
                channel.set(Html.fromHtml(context.getString(R.string.channel_payment,
                        historyPayment.channel_name)))
            } else {
                channel.set(Html.fromHtml(context.getString(R.string.channel_payment,
                        context.getString(R.string.updating))))
            }
            if (historyPayment.transaction_amount != null) {
                amount.set(Html.fromHtml(context.getString(R.string.amount_payment,
                        AppUtils.formatMoney(historyPayment.transaction_amount!!)
                                + context.getString(R.string.vnd))))
            } else {
                amount.set(Html.fromHtml(context.getString(R.string.amount_payment,
                        context.getString(R.string.updating))))
            }
            if (historyPayment.client_mobile != null) {
                description.set(Html.fromHtml(context.getString(R.string.description_payment,
                        historyPayment.client_mobile)))
            } else {
                description.set(Html.fromHtml(context.getString(R.string.description_payment,
                        context.getString(R.string.updating))))
            }
        }
    }
}

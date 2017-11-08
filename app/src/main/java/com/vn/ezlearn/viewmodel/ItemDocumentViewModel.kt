package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned

import com.vn.ezlearn.R
import com.vn.ezlearn.models.Document

/**
 * Created by FRAMGIA\nguyen.duc.manh on 08/11/2017.
 */

class ItemDocumentViewModel(context: Context, document: Document?) : BaseObservable() {
    var name: ObservableField<String> = ObservableField()
    var price: ObservableField<Spanned> = ObservableField()

    init {
        if (document != null) {
            if (document.name != null) {
                name.set(document.name)
            }
            if (document.price != null) {
                price.set(Html.fromHtml(context.getString(R.string.price, document.price)))
            }
        }
    }
}

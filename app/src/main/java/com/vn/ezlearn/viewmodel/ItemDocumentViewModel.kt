package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Html
import android.text.Spanned
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.models.Document

/**
 * Created by FRAMGIA\nguyen.duc.manh on 08/11/2017.
 */

class ItemDocumentViewModel(context: Context, document: Document?, isDownloaded: Boolean? = false) :
        BaseObservable() {
    var name: ObservableField<String> = ObservableField()
    var price: ObservableField<Spanned> = ObservableField()
    var image: ObservableInt = ObservableInt()
    var background: ObservableInt = ObservableInt()
    var visiableProgress: ObservableInt = ObservableInt(View.GONE)

    init {
        if (document != null) {
            if (document.name != null) {
                name.set(document.name)
            }
            if (document.price != null) {
                price.set(Html.fromHtml(context.getString(R.string.price, document.price)))
            }
            if (document.file_url != null) {
                if (document.file_url!!.endsWith("doc") || document.file_url!!.endsWith("docx")) {
                    image.set(R.mipmap.ic_doc)
                } else if (document.file_url!!.endsWith("pdf")) {
                    image.set(R.mipmap.ic_pdf)
                } else if (document.file_url!!.endsWith("ppt") || document.file_url!!.endsWith("pptx")) {
                    image.set(R.mipmap.ic_ppt)
                } else if (document.file_url!!.endsWith("jpg") || document.file_url!!.endsWith("png")
                        || document.file_url!!.endsWith("jpeg")) {
                    image.set(R.mipmap.ic_jpg)
                }
            }
            if (isDownloaded != null && isDownloaded) {
                background.set(R.drawable.bg_exam)
            } else {
                background.set(R.color.white)
            }

        }
    }
}

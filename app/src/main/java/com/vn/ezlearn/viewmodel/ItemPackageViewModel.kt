package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.models.Package
import com.vn.ezlearn.utils.AppUtils

/**
 * Created by FRAMGIA\nguyen.duc.manh on 06/12/2017.
 */

class ItemPackageViewModel(private val context: Context, aPackage: Package?) : BaseObservable() {
    var packageName: ObservableField<String>? = null
    var packageMoney: ObservableField<String>? = null
    var packageDescription: ObservableField<Spanned>? = null
    var packageImage: ObservableField<String>? = null

    init {
        aPackage?.let {
            packageName = ObservableField(it.package_display_name!!)
            packageMoney = ObservableField(AppUtils.formatMoney(it.price!!) + " " + context.getString(R.string.vnd))
//            it.description = it.description!!.replace("<p>&nbsp;</p>", "").trim()
            packageDescription = ObservableField(Html.fromHtml(
                    it.description!!.substring(3, it.description!!.length)
                            .substring(0, it.description!!.length - 4)))
            packageImage = ObservableField(BuildConfig.ENDPOINT_DOWNLOAD + it.file_image)

        }

    }
}

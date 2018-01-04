package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Html
import android.text.Spanned
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.models.Exam
import com.vn.ezlearn.utils.AppUtils

@Suppress("DEPRECATION")
/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ItemExamViewModel(private val context: Context, private val exam: Exam?) : BaseObservable() {
    var name: ObservableField<String> = ObservableField()
    var time: ObservableField<String> = ObservableField()
    var dateStartEnd: ObservableField<String> = ObservableField()
    var viewCount: ObservableField<String> = ObservableField()
    var doingCount: ObservableField<String> = ObservableField()
    var price: ObservableField<Spanned> = ObservableField()

    var visiableFree: ObservableInt = ObservableInt(View.GONE)

    init {
        bindExam()
    }

    private fun bindExam() {
        if (exam != null) {
            if (!exam.subject_code.isEmpty()) {
                name.set(exam.subject_code)
            }
            if (exam.time != null) {
                time.set(exam.time.toString() + " " + context.getString(R.string.minute))
            }
            if (exam.start_date != null && exam.end_date != null) {
                dateStartEnd.set(AppUtils.formatDateTime(exam.start_date!!) + " - "
                        + AppUtils.formatDateTime(exam.end_date!!))
            }
            if (exam.is_free == 1) {
                visiableFree.set(View.VISIBLE)
            } else {
                visiableFree.set(View.GONE)
            }
            if (exam.view_count != null) {
                viewCount.set(exam.view_count.toString() + " " + context.getString(R.string.view))
            } else {
                viewCount.set(context.getString(R.string.updating))
            }
            if (exam.doing_count != null) {
                doingCount.set(exam.doing_count.toString() + " " + context.getString(R.string.doing))
            } else {
                viewCount.set(context.getString(R.string.updating))
            }
            if (exam.is_free == 1) {
                price.set(Html.fromHtml(context.getString(R.string.price, context.getString(R.string.free))))
            } else {
                if (exam.price != null) {
                    price.set(Html.fromHtml(context.getString(
                            R.string.price, AppUtils.formatMoney(exam.price!!.toLong())) + " " +
                            context.getString(R.string.vnd)))
                } else {
                    viewCount.set(context.getString(R.string.updating))
                }
            }

        }
    }
}

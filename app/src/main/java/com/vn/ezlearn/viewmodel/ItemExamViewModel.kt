package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View

import com.vn.ezlearn.R
import com.vn.ezlearn.models.Exam

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ItemExamViewModel(private val context: Context, private val exam: Exam?) : BaseObservable() {
    var name: ObservableField<String> = ObservableField()
    var time: ObservableField<String> = ObservableField()
    var date_start_end: ObservableField<String> = ObservableField()
    var total_question: ObservableField<String> = ObservableField()
    var total_view: ObservableField<String> = ObservableField()

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
                date_start_end.set(exam.start_date + " " + exam.end_date)
            }
            if (exam.is_free == 1) {
                visiableFree.set(View.VISIBLE)
            } else {
                visiableFree.set(View.GONE)
            }
            if (exam.total_view != null) {
                total_view.set(exam.total_view.toString() + " " + context.getString(R.string.view))
            } else {
                total_view.set("N/A")
            }
        }
    }
}

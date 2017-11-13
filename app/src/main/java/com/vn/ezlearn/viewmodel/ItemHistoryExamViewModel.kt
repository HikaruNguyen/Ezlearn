package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.TextUtils
import com.vn.ezlearn.R
import com.vn.ezlearn.models.HistoryExam

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class ItemHistoryExamViewModel(context: Context, historyExam: HistoryExam?) : BaseObservable() {
    var ExamCode: ObservableField<String> = ObservableField("")
    var timeStart: ObservableField<String> = ObservableField("")
    var mark: ObservableField<String> = ObservableField("")

    init {
        if (historyExam != null) {
            if (historyExam.subject_id != null) {
                ExamCode.set("" + historyExam.subject_id)
            }
            if (!TextUtils.isEmpty(historyExam.time_start)) {
                timeStart.set(historyExam.time_start)
            } else {
                timeStart.set(context.getString(R.string.updating))
            }
            if (historyExam.mark != null) {
                mark.set("" + historyExam.mark)
            } else {
                mark.set("0")
            }
        }
    }
}

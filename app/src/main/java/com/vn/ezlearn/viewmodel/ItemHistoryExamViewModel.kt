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
    var examCode: ObservableField<String> = ObservableField("")
    var timeStart: ObservableField<String> = ObservableField("")
    var mark: ObservableField<String> = ObservableField("")

    init {
        if (historyExam != null) {
            if (!TextUtils.isEmpty(historyExam.subject_code)) {
                examCode.set(historyExam.subject_code)
            }
            if (!TextUtils.isEmpty(historyExam.time_start)) {
                timeStart.set(context.getString(R.string.history_exam_time_start, historyExam.time_start))
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

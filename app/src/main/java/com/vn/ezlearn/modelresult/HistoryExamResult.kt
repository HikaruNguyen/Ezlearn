package com.vn.ezlearn.modelresult

import com.vn.ezlearn.models.HistoryExam

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class HistoryExamResult {
    var success: Boolean = false
    var data: HistoryExamData? = null

    class HistoryExamData {
        var list: List<HistoryExam>? = null
        var totalCount: Int? = null
    }
}

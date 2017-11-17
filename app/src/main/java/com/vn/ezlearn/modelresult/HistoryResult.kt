package com.vn.ezlearn.modelresult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class HistoryResult<T> {
    var success: Boolean = false
    var data: HistoryExamData<T>? = null

    class HistoryExamData<T> {
        var list: List<T>? = null
        var totalCount: Int? = null
    }
}

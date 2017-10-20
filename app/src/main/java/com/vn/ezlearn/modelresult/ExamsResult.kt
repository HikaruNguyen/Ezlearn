package com.vn.ezlearn.modelresult

import com.vn.ezlearn.models.Category
import com.vn.ezlearn.models.Exam

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ExamsResult {
    var success: Boolean = false
    var data: ExamsData? = null

    class ExamsData {
        var category: Category? = null
        var list: List<Exam>? = null
        var totalCount: Int = 0
    }
}

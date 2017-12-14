package com.vn.ezlearn.modelresult

import com.vn.ezlearn.models.Question

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class QuestionResult {
    var success: Boolean = false
    var data: QuestionData? = null

    class QuestionData {
        var data: List<Question>? = null
        var message: String? = null
    }
}

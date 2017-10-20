package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class Answer {

    var id: Int? = null
    var question_id: Int? = null
    var answer: String? = null
    var is_true: Int = 0
    var mark: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var status: Int? = null
    var weight: String? = null

    companion object {
        val TRUE = 1
    }
}

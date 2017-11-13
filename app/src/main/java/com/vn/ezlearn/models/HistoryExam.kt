package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class HistoryExam {
    var id: Int? = null
    var user_id: Int? = null
    var subject_id: Int? = null
    var grammar: String? = null
    var reading_answer: String? = null
    var reading_fill: String? = null
    var reading_image: String? = null
    var listening: String? = null
    var writing: String? = null
    var time_start: String? = null
    var mark: Float? = null
    var time_end: String? = null
    var amount_true: Int? = null
    var amount_false: Int? = null
    var amount_miss: Int? = null
    var answers: String? = null
    var amount_wait: Int? = null
    var answers_wait: String? = null

    class AnswerHistory {
        var qId: Int? = null
        var answer: String? = null
        var approved: Int? = null
        var answer_true: Int? = null

    }
}

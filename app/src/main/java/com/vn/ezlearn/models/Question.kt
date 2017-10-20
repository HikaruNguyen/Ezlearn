package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class Question {
    var region: Region? = null
    var type: Int? = null
    var question: List<Content>? = null
    var reading: List<Reading>? = null

    companion object {
        val TYPE_QUESTION = 1
        val TYPE_READING = 2
    }
}

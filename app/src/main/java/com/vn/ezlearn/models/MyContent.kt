package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class MyContent {
    var question_id: Int?
    var typeQuestion: Int = 0

    var region: Region
    var type: Int? = null
    var content: Content
    var passage: String = ""

    var point: Float? = null

    var myAnswer: Int = -1
    var myInput: String = ""
    var isCorrect: Boolean = false

    var isReview: Boolean = false

    constructor(question_id: Int, region: Region, type: Int?, content: Content, point: Float?,
                isReview: Boolean) {
        this.question_id = question_id
        this.region = region
        this.type = type
        this.content = content
        this.point = point
        this.isCorrect = false
        this.typeQuestion = TYPE_NO_ANSWER
        myAnswer = -1
        myInput = ""
        this.isReview = isReview
    }

    constructor(question_id: Int, region: Region, type: Int?, content: Content, passage: String,
                point: Float?, isReview: Boolean) {
        this.question_id = question_id
        this.region = region
        this.type = type
        this.content = content
        this.passage = passage
        this.point = point
        this.isCorrect = false
        this.typeQuestion = TYPE_NO_ANSWER
        myAnswer = -1
        myInput = ""
        this.isReview = isReview
    }

    companion object {
        val TYPE_ANSWERED = 1
        val TYPE_NO_ANSWER = 2
        val TYPE_LATE = 3
    }
}

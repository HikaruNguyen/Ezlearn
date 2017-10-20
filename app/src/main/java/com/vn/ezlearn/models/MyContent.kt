package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class MyContent {

    var typeQuestion: Int = 0

    var region: Region
    var type: Int? = null
    var content: Content
    var passage: String = ""

    var point: Float? = null
    var isCorrect: Boolean = false

    constructor(region: Region, type: Int?, content: Content, point: Float?) {
        this.region = region
        this.type = type
        this.content = content
        this.point = point
        this.isCorrect = false
        this.typeQuestion = TYPE_NO_ANSWER
    }

    constructor(region: Region, type: Int?, content: Content, passage: String, point: Float?) {
        this.region = region
        this.type = type
        this.content = content
        this.passage = passage
        this.point = point
        this.isCorrect = false
        this.typeQuestion = TYPE_NO_ANSWER
    }

    companion object {
        val TYPE_ANSWERED = 1
        val TYPE_NO_ANSWER = 2
        val TYPE_LATE = 3
    }
}

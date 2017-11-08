package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

class HomeObject {

    var header: String = ""
    var exam: Exam? = null
    var bannerList: List<Banner>? = null
    var type: Int = 0

    constructor(header: String) {
        this.header = header
        type = TYPE_HEADER
    }

    constructor(exam: Exam) {
        this.exam = exam
        type = TYPE_EXAM
    }

    constructor(bannerList: List<Banner>) {
        this.bannerList = bannerList
        type = TYPE_SLIDE
    }

    companion object {
        val TYPE_SLIDE = 1
        val TYPE_HEADER = 2
        val TYPE_EXAM = 3
    }
}

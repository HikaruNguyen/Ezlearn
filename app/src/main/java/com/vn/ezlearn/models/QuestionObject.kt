package com.vn.ezlearn.models

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

class QuestionObject {

    var part: String = ""
    var fileAudio: String? = null
    var list: List<MyContent>? = null
    var type: Int = 0

    constructor(part: String) {
        this.part = part
        this.fileAudio = null
        this.type = TYPE_PART
    }

    constructor(part: String, fileAudio: String?) {
        this.part = part
        this.fileAudio = fileAudio
        this.type = TYPE_PART
    }

    constructor(list: List<MyContent>) {
        this.list = list
        this.type = TYPE_VIEWPAGER
    }

    companion object {
        val TYPE_PART = 2
        val TYPE_VIEWPAGER = 3
    }
}

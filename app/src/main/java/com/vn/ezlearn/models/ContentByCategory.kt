package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 08/11/2017.
 */

class ContentByCategory(var exam: Exam?, var document: Document?,
                        var contentType: Int?) {
    companion object {

        val CONTENT_TYPE_EXAM = 2
        val CONTENT_TYPE_DOCUMENT = 3
        val CONTENT_TYPE_IMAGE = 4
        val CONTENT_TYPE_EXTEND = 5
    }


}

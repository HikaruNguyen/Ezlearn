package com.vn.ezlearn.utils

import com.vn.ezlearn.models.MyContent

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/10/2017.
 */

class QuestionUtils {
    var myContentList: List<MyContent>? = null

    fun clearMyContentList() {
        myContentList = null
    }

    companion object {
        private var questionUtils: QuestionUtils? = null

        val instance: QuestionUtils
            get() {
                if (questionUtils == null) {
                    questionUtils = QuestionUtils()
                }
                return questionUtils as QuestionUtils
            }
    }
}

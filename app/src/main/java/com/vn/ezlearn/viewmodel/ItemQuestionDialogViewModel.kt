package com.vn.ezlearn.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt

import com.vn.ezlearn.R
import com.vn.ezlearn.models.MyContent

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class ItemQuestionDialogViewModel(content: MyContent, position: Int,
                                  isShowPoint: Boolean) : BaseObservable() {

    var backgroundItem: ObservableInt = ObservableInt()
    var position: ObservableField<String>

    init {
        if (content.typeQuestion == MyContent.TYPE_NO_ANSWER) {
            backgroundItem.set(R.drawable.bg_question_unanswer)
        } else {
            if (isShowPoint) {
                if (content.isCorrect) {
                    backgroundItem.set(R.drawable.bg_question_correct)
                } else {
                    backgroundItem.set(R.drawable.bg_question_no_correct)
                }
            } else {
                when {
                    content.typeQuestion == MyContent.TYPE_ANSWERED -> backgroundItem.set(R.drawable.bg_question_answered)
                    content.typeQuestion == MyContent.TYPE_LATE -> backgroundItem.set(R.drawable.bg_question_review)
                    else -> backgroundItem.set(R.drawable.bg_question_unanswer)
                }
            }

        }

        this.position = ObservableField(position.toString())
    }
}

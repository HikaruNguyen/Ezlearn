package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;

import com.vn.ezlearn.R;
import com.vn.ezlearn.model.Question;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

public class ItemQuestionDialogViewModel extends BaseObservable {
    private Context context;
    private Question question;

    public ObservableInt backgroundItem;

    public ItemQuestionDialogViewModel(Context context, Question question) {
        this.context = context;
        this.question = question;
        backgroundItem = new ObservableInt();
        if (question.type == Question.TYPE_ANSWERED) {
            backgroundItem.set(R.drawable.bg_question_answered);
        } else if (question.type == Question.TYPE_UNANSWER) {
            backgroundItem.set(R.drawable.bg_question_unanswer);
        } else if (question.type == Question.TYPE_LATE) {
            backgroundItem.set(R.drawable.bg_question_review);
        } else {
            backgroundItem.set(R.drawable.bg_question_unanswer);
        }
    }
}

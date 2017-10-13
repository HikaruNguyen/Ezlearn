package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.vn.ezlearn.R;
import com.vn.ezlearn.models.MyContent;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

public class ItemQuestionDialogViewModel extends BaseObservable {
    private Context context;
    private MyContent content;

    public ObservableInt backgroundItem;
    public ObservableField<String> position;

    public ItemQuestionDialogViewModel(Context context, MyContent content, int position,
                                       boolean isShowPoint) {
        this.context = context;
        this.content = content;
        backgroundItem = new ObservableInt();
        if (content.typeQuestion == MyContent.TYPE_NO_ANSWER) {
            backgroundItem.set(R.drawable.bg_question_unanswer);
        } else {
            if (isShowPoint) {
                if (content.isCorrect) {
                    backgroundItem.set(R.drawable.bg_question_correct);
                }else {
                    backgroundItem.set(R.drawable.bg_question_no_correct);
                }
            } else {
                if (content.typeQuestion == MyContent.TYPE_ANSWERED) {
                    backgroundItem.set(R.drawable.bg_question_answered);
                } else if (content.typeQuestion == MyContent.TYPE_LATE) {
                    backgroundItem.set(R.drawable.bg_question_review);
                } else {
                    backgroundItem.set(R.drawable.bg_question_unanswer);
                }
            }

        }

        this.position = new ObservableField<>(String.valueOf(position));
    }
}

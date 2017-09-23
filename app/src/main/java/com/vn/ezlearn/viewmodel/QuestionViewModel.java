package com.vn.ezlearn.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.vn.ezlearn.models.Question;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class QuestionViewModel extends BaseObservable {

    private Context context;
    private Question question;

    public ObservableField<Spanned> textPassage;
    public ObservableField<Spanned> textQuestion;
    public ObservableInt visiablePassage;
    public ObservableInt visiableQuestion;

    public QuestionViewModel(Activity context, Question question) {
        this.context = context;
        this.question = question;
        textPassage = new ObservableField<>();
        textQuestion = new ObservableField<>();
        visiablePassage = new ObservableInt(View.GONE);
        visiableQuestion = new ObservableInt(View.GONE);
        if (question != null) {
            setQuestionData();
        }
    }

    private void setQuestionData() {
        if (question.passage != null) {
            textPassage.set(Html.fromHtml(question.passage));
            visiablePassage.set(View.VISIBLE);
        } else {
            visiablePassage.set(View.GONE);
        }
        if (question.question != null) {
            textQuestion.set(Html.fromHtml(question.question));
            visiableQuestion.set(View.VISIBLE);
        }else {
            visiableQuestion.set(View.GONE);
        }
    }
}

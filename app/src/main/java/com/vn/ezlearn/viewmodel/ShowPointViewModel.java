package com.vn.ezlearn.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.Html;
import android.text.Spanned;

import com.vn.ezlearn.R;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 12/10/2017.
 */

public class ShowPointViewModel extends BaseObservable {
    public float point;
    public Context context;

    public ObservableField<String> tvPoint;
    public ObservableField<String> tvNumAnswerCorrect;
    public ObservableField<String> tvNumAnswerNoCorrect;
    public ObservableField<String> tvNumNoAnswer;
    public ObservableField<Spanned> tvTimeAnswer;

    public ObservableField<String> tvExamName;

    @SuppressLint("DefaultLocale")
    public ShowPointViewModel(Context context, float point, int numAnswerCorrect,
                              int numAnswerNoCorrect, int numNoAnswer,
                              int hourAnswer, int minuteAnswer, int secondAnswer,
                              String examName) {
        this.point = point;
        this.context = context;

        tvPoint = new ObservableField<>(String.format("%.1f", point) + " " + context.getString(R.string.point));
        tvNumAnswerCorrect = new ObservableField<>(context.getString(
                R.string.answer_correct, numAnswerNoCorrect));
        tvNumAnswerNoCorrect = new ObservableField<>(context.getString(
                R.string.answer_no_correct, numAnswerCorrect));
        tvNumNoAnswer = new ObservableField<>(context.getString(
                R.string.no_answer, numNoAnswer));
        tvTimeAnswer = new ObservableField<>(Html.fromHtml(context.getString(R.string.time_answer,
                hourAnswer, minuteAnswer, secondAnswer)));
        tvExamName = new ObservableField<>(examName);
    }
}

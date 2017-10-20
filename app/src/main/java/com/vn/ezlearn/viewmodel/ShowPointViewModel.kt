package com.vn.ezlearn.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned

import com.vn.ezlearn.R

/**
 * Created by FRAMGIA\nguyen.duc.manh on 12/10/2017.
 */

class ShowPointViewModel @SuppressLint("DefaultLocale")
constructor(var context: Context, var point: Float, numAnswerCorrect: Int,
            numAnswerNoCorrect: Int, numNoAnswer: Int,
            hourAnswer: Int, minuteAnswer: Int, secondAnswer: Int,
            examName: String) : BaseObservable() {

    var tvPoint: ObservableField<String> = ObservableField(String.format("%.1f", point) + " "
            + context.getString(R.string.point))
    var tvNumAnswerCorrect: ObservableField<String> = ObservableField(context.getString(
            R.string.answer_correct, numAnswerNoCorrect))
    var tvNumAnswerNoCorrect: ObservableField<String> = ObservableField(context.getString(
            R.string.answer_no_correct, numAnswerCorrect))
    var tvNumNoAnswer: ObservableField<String> = ObservableField(context.getString(
            R.string.no_answer, numNoAnswer))
    var tvTimeAnswer: ObservableField<Spanned> = ObservableField(Html.fromHtml(context.getString(
            R.string.time_answer, hourAnswer, minuteAnswer, secondAnswer)))

    var tvExamName: ObservableField<String> = ObservableField(examName)

}

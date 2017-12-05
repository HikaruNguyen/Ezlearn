package com.vn.ezlearn.viewmodel

import android.app.Activity
import android.content.Context
import android.databinding.*
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import com.vn.ezlearn.R
import com.vn.ezlearn.models.Content
import com.vn.ezlearn.models.MyContent

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

class QuestionViewModel(context: Activity, private val content: MyContent?, position: Int,
                        size: Int) : BaseObservable() {

    private val context: Context
    var textPassage: ObservableField<Spanned>
    var textQuestion: ObservableField<Spanned>
    var visiablePassage: ObservableInt
    var visiableQuestion: ObservableInt

    var isNeedReview: ObservableBoolean
    var textNeedReview: ObservableField<Spanned>
    var drawableFlag: ObservableInt

    var answerA: ObservableField<Spanned>
    var answerB: ObservableField<Spanned>
    var answerC: ObservableField<Spanned>
    var answerD: ObservableField<Spanned>

    var tvQuestion: ObservableField<String>

    var suggest: ObservableField<Spanned>
    var visiableSuggest: ObservableInt
    var visiableMyAnswer: ObservableInt

    var visiableRadio: ObservableInt = ObservableInt(View.GONE)
    var visiableInput: ObservableInt = ObservableInt(View.GONE)
    var enableInput: ObservableBoolean = ObservableBoolean(false)

    var yourInput: ObservableField<Spanned>
            = ObservableField(Html.fromHtml(context.getString(R.string.your_answer, "")))
    var correctInput: ObservableField<Spanned>
            = ObservableField(Html.fromHtml(""))

    init {
        this.context = context
        textPassage = ObservableField()
        textQuestion = ObservableField()
        visiablePassage = ObservableInt(View.GONE)
        visiableQuestion = ObservableInt(View.GONE)
        isNeedReview = ObservableBoolean(false)
        textNeedReview = ObservableField(
                Html.fromHtml(context.getString(R.string.need_to_review)))
        drawableFlag = ObservableInt(R.mipmap.ic_flag)

        answerA = ObservableField()
        answerB = ObservableField()
        answerC = ObservableField()
        answerD = ObservableField()

        visiableSuggest = ObservableInt(View.GONE)
        visiableMyAnswer = ObservableInt(View.GONE)
        suggest = ObservableField()

        tvQuestion = ObservableField(
                context.getString(R.string.question) + " " + (position + 1) + "/" + size)

        if (content != null) {
            setQuestionData()
        }
        checkShowAnswerType()
    }

    private fun setQuestionData() {
        //        if (content.type == Question.TYPE_READING) {
        if (content != null && !content.passage.isEmpty()) {
            textPassage.set(Html.fromHtml(content.passage.replace("&nbsp;", "")))
            visiablePassage.set(View.VISIBLE)
        } else {
            visiablePassage.set(View.GONE)
        }
        //        } else {
        if (content != null && !content.content.content.isEmpty()) {
            textQuestion.set(Html.fromHtml(content.content.content.replace("<p>", "")
                    .replace("</p>", "")))
            visiableQuestion.set(View.VISIBLE)
        } else {
            visiableQuestion.set(View.GONE)
        }
        if (content != null && content.content.answer_list != null
                && content.content.answer_list!!.size >= 4) {
            answerA.set(Html.fromHtml("A. " + content.content.answer_list!![0].answer!!
                    .replace("<p>", "").replace("</p>", "")))
            answerB.set(Html.fromHtml("B. " + content.content.answer_list!![1].answer!!
                    .replace("<p>", "").replace("</p>", "")))
            answerC.set(Html.fromHtml("C. " + content.content.answer_list!![2].answer!!
                    .replace("<p>", "").replace("</p>", "")))
            answerD.set(Html.fromHtml("D. " + content.content.answer_list!![3].answer!!
                    .replace("<p>", "").replace("</p>", "")))
        }
        if (content != null && content.content.suggest != null) {
            suggest.set(Html.fromHtml(content.content.suggest))
        }
    }

    fun setNeedReview() {
        isNeedReview.set(!isNeedReview.get())
        if (isNeedReview.get()) {
            textNeedReview.set(Html.fromHtml(context.getString(R.string.cancel_review)))
            drawableFlag.set(R.mipmap.ic_flag_red)
        } else {
            textNeedReview.set(Html.fromHtml(context.getString(R.string.need_to_review)))
            drawableFlag.set(R.mipmap.ic_flag)

        }
    }

    fun showSuggest() {
        visiableSuggest.set(View.VISIBLE)
        if (content?.content?.answer_show?.contentEquals(Content.ANSWER_SHOW_INPUT)!!) {
            visiableMyAnswer.set(View.VISIBLE)
        }
    }

    private fun checkShowAnswerType() {
        if (content?.content?.answer_show!!.contentEquals(Content.ANSWER_SHOW_DEFAULT)) {
            visiableRadio.set(View.VISIBLE)
            visiableInput.set(View.GONE)
            enableInput.set(false)
        } else if (content.content.answer_show!!.contentEquals(Content.ANSWER_SHOW_INPUT)) {
            visiableRadio.set(View.GONE)
            visiableInput.set(View.VISIBLE)
            enableInput.set(true)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageResource")
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
}

package com.vn.ezlearn.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;

import com.vn.ezlearn.R;
import com.vn.ezlearn.models.MyContent;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class QuestionViewModel extends BaseObservable {

    private Context context;
    private MyContent content;
    public ObservableField<Spanned> textPassage;
    public ObservableField<Spanned> textQuestion;
    public ObservableInt visiablePassage;
    public ObservableInt visiableQuestion;

    public ObservableBoolean isNeedReview;
    public ObservableField<Spanned> textNeedReview;
    public ObservableInt drawableFlag;

    public ObservableField<Spanned> answerA;
    public ObservableField<Spanned> answerB;
    public ObservableField<Spanned> answerC;
    public ObservableField<Spanned> answerD;

    public ObservableField<String> tvQuestion;

    public ObservableInt visiableNext;
    public ObservableInt visiablePre;

    public ObservableField<Spanned> suggest;
    public ObservableInt visiableSuggest;

    public QuestionViewModel(Activity context, MyContent content, int position, int size) {
        this.context = context;
        this.content = content;
        textPassage = new ObservableField<>();
        textQuestion = new ObservableField<>();
        visiablePassage = new ObservableInt(View.GONE);
        visiableQuestion = new ObservableInt(View.GONE);
        isNeedReview = new ObservableBoolean(false);
        textNeedReview = new ObservableField<>(
                Html.fromHtml(context.getString(R.string.needReview)));
        drawableFlag = new ObservableInt(R.mipmap.ic_flag);

        answerA = new ObservableField<>();
        answerB = new ObservableField<>();
        answerC = new ObservableField<>();
        answerD = new ObservableField<>();

        visiableSuggest = new ObservableInt(View.GONE);
        suggest = new ObservableField<>();

        tvQuestion = new ObservableField<>(
                context.getString(R.string.question) + " " + (position + 1) + "/" + size);

        if (content != null) {
            setQuestionData();
        }

    }

    private void setQuestionData() {
//        if (content.type == Question.TYPE_READING) {
        if (content != null && content.passage != null && !content.passage.isEmpty()) {
            textPassage.set(Html.fromHtml(content.passage.replace("&nbsp;", "")));
            visiablePassage.set(View.VISIBLE);
        } else {
            visiablePassage.set(View.GONE);
        }
//        } else {
        if (content != null && content.content != null && content.content.content != null
                && !content.content.content.isEmpty()) {
            textQuestion.set(Html.fromHtml(content.content.content
                    .replace("<p>", "").replace("</p>", "")));
            visiableQuestion.set(View.VISIBLE);
        } else {
            visiableQuestion.set(View.GONE);
        }
        if (content != null && content.content != null && content.content.answer_list != null
                && content.content.answer_list.size() >= 4) {
            answerA.set(Html.fromHtml(content.content.answer_list.get(0).answer
                    .replace("<p>", "").replace("</p>", "")));
            answerB.set(Html.fromHtml(content.content.answer_list.get(1).answer
                    .replace("<p>", "").replace("</p>", "")));
            answerC.set(Html.fromHtml(content.content.answer_list.get(2).answer
                    .replace("<p>", "").replace("</p>", "")));
            answerD.set(Html.fromHtml(content.content.answer_list.get(3).answer
                    .replace("<p>", "").replace("</p>", "")));
        }
        if (content != null && content.content != null && content.content.suggest != null) {
            suggest.set(Html.fromHtml(content.content.suggest));
        }
    }

    public void setNeedReview() {
        isNeedReview.set(!isNeedReview.get());
        if (isNeedReview.get()) {
            textNeedReview.set(Html.fromHtml(context.getString(R.string.unNeedReview)));
            drawableFlag.set(R.mipmap.ic_flag_red);
        } else {
            textNeedReview.set(Html.fromHtml(context.getString(R.string.needReview)));
            drawableFlag.set(R.mipmap.ic_flag);

        }
    }

    public void showSuggest(){
        visiableSuggest.set(View.VISIBLE);
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}

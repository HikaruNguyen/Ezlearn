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

    public ObservableBoolean isNeedReview;
    public ObservableField<Spanned> textNeedReview;
    public ObservableInt drawableFlag;

    public QuestionViewModel(Activity context, Question question) {
        this.context = context;
        this.question = question;
        textPassage = new ObservableField<>();
        textQuestion = new ObservableField<>();
        visiablePassage = new ObservableInt(View.GONE);
        visiableQuestion = new ObservableInt(View.GONE);
        isNeedReview = new ObservableBoolean(false);
        textNeedReview = new ObservableField<>(
                Html.fromHtml(context.getString(R.string.needReview)));
        drawableFlag = new ObservableInt(R.mipmap.ic_flag);
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
        } else {
            visiableQuestion.set(View.GONE);
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

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}

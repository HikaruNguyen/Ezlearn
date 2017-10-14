package com.vn.ezlearn.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.FragmentQuestionBinding;
import com.vn.ezlearn.interfaces.OnCheckAnswerListener;
import com.vn.ezlearn.models.Answer;
import com.vn.ezlearn.models.MyContent;
import com.vn.ezlearn.viewmodel.QuestionViewModel;

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

public class QuestionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SIZE_NUMBER = "size_number";
    private FragmentQuestionBinding fragmentQuestionBinding;
    private QuestionViewModel questionViewModel;
    private int position;
    private int size;
    private MyContent content;
    private OnCheckAnswerListener onCheckAnswerListener;
    private int answer = -1;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(int position, int size) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        args.putInt(ARG_SIZE_NUMBER, size);
        fragment.setArguments(args);
        return fragment;
    }

    public void setQuestion(MyContent content, OnCheckAnswerListener onCheckAnswerListener) {
        this.content = content;
        this.onCheckAnswerListener = onCheckAnswerListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_SECTION_NUMBER);
            size = getArguments().getInt(ARG_SIZE_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQuestionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_question, container, false);
        questionViewModel = new QuestionViewModel(getActivity(), content, position, size);
        fragmentQuestionBinding.setQuestionViewModel(questionViewModel);
//        bindData();
        event();
        return fragmentQuestionBinding.getRoot();
    }

    private void event() {
        fragmentQuestionBinding.rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rdAnswerA:
                        answer(0);
                        break;
                    case R.id.rdAnswerB:
                        answer(1);
                        break;
                    case R.id.rdAnswerC:
                        answer(2);
                        break;
                    case R.id.rdAnswerD:
                        answer(3);
                        break;
                    default:
                        break;
                }
            }
        });

        fragmentQuestionBinding.tvNeedReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionViewModel.setNeedReview();
                onCheckAnswerListener.onNeedReview(position);
            }
        });
    }

    public void answer(int answer) {
        onCheckAnswerListener.OnCheckAnswer(position, answer);
        this.answer = answer;
    }

    public void showSuggest() {
        questionViewModel.showSuggest();
        hideButtonRadioButton(fragmentQuestionBinding.rdAnswerA);
        hideButtonRadioButton(fragmentQuestionBinding.rdAnswerB);
        hideButtonRadioButton(fragmentQuestionBinding.rdAnswerC);
        hideButtonRadioButton(fragmentQuestionBinding.rdAnswerD);

        if (content.content.answer_list.get(0).is_true.intValue() == Answer.IS_TRUE) {
            setStyleCorrect(fragmentQuestionBinding.rdAnswerA);
        } else if (content.content.answer_list.get(1).is_true.intValue() == Answer.IS_TRUE) {
            setStyleCorrect(fragmentQuestionBinding.rdAnswerB);
        } else if (content.content.answer_list.get(2).is_true.intValue() == Answer.IS_TRUE) {
            setStyleCorrect(fragmentQuestionBinding.rdAnswerC);
        } else if (content.content.answer_list.get(3).is_true.intValue() == Answer.IS_TRUE) {
            setStyleCorrect(fragmentQuestionBinding.rdAnswerD);
        }
        if (answer >= 0) {
            if (content.content.answer_list.get(answer).is_true.intValue() != Answer.IS_TRUE) {
                switch (answer) {
                    case 0:
                        setStyleNoCorrect(fragmentQuestionBinding.rdAnswerA);
                        break;
                    case 1:
                        setStyleNoCorrect(fragmentQuestionBinding.rdAnswerB);
                        break;
                    case 2:
                        setStyleNoCorrect(fragmentQuestionBinding.rdAnswerC);
                        break;
                    case 3:
                        setStyleNoCorrect(fragmentQuestionBinding.rdAnswerD);
                        break;
                }
            } else {
                switch (answer) {
                    case 0:
                        showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerA);
                        break;
                    case 1:
                        showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerB);
                        break;
                    case 2:
                        showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerC);
                        break;
                    case 3:
                        showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerD);
                        break;
                }
            }
        }

    }

    public void setStyleCorrect(RadioButton radioButton) {
        radioButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
        radioButton.setTypeface(radioButton.getTypeface(), Typeface.BOLD_ITALIC);
    }

    public void setStyleNoCorrect(RadioButton radioButton) {
        radioButton.setPaintFlags(radioButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void hideButtonRadioButton(RadioButton radioButton) {
        radioButton.setButtonDrawable(android.R.color.transparent);
        radioButton.setPadding(0, 0, 0, 0);
    }

    public void showTickCorrectAnswer(RadioButton radioButton) {
        Drawable img = getContext().getResources().getDrawable(R.drawable.tick);
        img.setBounds(0, 0, 32, 32);
        radioButton.setCompoundDrawables(img, null, null, null);
    }
}
package com.vn.ezlearn.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.FragmentQuestionBinding;
import com.vn.ezlearn.interfaces.OnCheckAnswerListener;
import com.vn.ezlearn.models.Content;
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
    private Content content;
    private OnCheckAnswerListener onCheckAnswerListener;

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

    public void setQuestion(Content content, OnCheckAnswerListener onCheckAnswerListener) {
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
        questionViewModel = new QuestionViewModel(getActivity(), content);
        fragmentQuestionBinding.setQuestionViewModel(questionViewModel);
        bindData();
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
        onCheckAnswerListener.OnCheckAnswer(position);
    }

    private void bindData() {
        fragmentQuestionBinding.rdAnswerA.setText(Html.fromHtml("either"));
        fragmentQuestionBinding.rdAnswerB.setText(Html.fromHtml("nor"));
        fragmentQuestionBinding.rdAnswerC.setText(Html.fromHtml("or"));
        fragmentQuestionBinding.rdAnswerD.setText(Html.fromHtml("both"));
        fragmentQuestionBinding.tvQuestionNum.setText(
                getString(R.string.question) + " " + (position + 1) + "/" + size);

    }
}
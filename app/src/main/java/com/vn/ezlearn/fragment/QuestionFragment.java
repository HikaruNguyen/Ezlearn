package com.vn.ezlearn.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.FragmentQuestionBinding;

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

public class QuestionFragment  extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentQuestionBinding fragmentQuestionBinding;
    private int position;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(int sectionNumber) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQuestionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_question, container, false);
        fragmentQuestionBinding.tvPassage.setText(Html.fromHtml("<p><strong>HOW TRANSPORTATION AFFECTS OUR LIVES</strong></p>\n" +
                "\n" +
                "<p>Without transportation, our modern society could not exist. We would have no metals, no coal, and no oil (31)______ would we have any products made from these materials. Besides, we would have to (32) ______ most of our time raising food - and the food would be limited to the kinds that could grow in the climate and soil of our own neighborhoods.</p>\n" +
                "\n" +
                "<p>Transportation also affects our lives in (33) ______ ways. Transportation can speed a doctor to the sides of a sick person, even if the (34)______ lives on an isolated farm. It can take police to the scene of a crime within moments of being notified. Transportation enables teams of athletes to compete in national and international sports (35)______. In times of disasters, transportation can rush aid to persons in areas stricken by floods, famines, and earthquakes.</p>"));


        fragmentQuestionBinding.rdAnswerA.setText(Html.fromHtml("either"));
        fragmentQuestionBinding.rdAnswerB.setText(Html.fromHtml("nor"));
        fragmentQuestionBinding.rdAnswerC.setText(Html.fromHtml("or"));
        fragmentQuestionBinding.rdAnswerD.setText(Html.fromHtml("both"));
        fragmentQuestionBinding.tvQuestion.setText("Question: " + position);

        return fragmentQuestionBinding.getRoot();
    }
}
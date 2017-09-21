package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.DialogListQuestionAdapter;
import com.vn.ezlearn.databinding.ActivityQuestionBinding;
import com.vn.ezlearn.databinding.DialogListAnswerBinding;
import com.vn.ezlearn.databinding.FragmentQuestionBinding;
import com.vn.ezlearn.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    private ActivityQuestionBinding questionBinding;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private AlertDialog dialogListAnswer;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionBinding = DataBindingUtil.setContentView(this, R.layout.activity_question);

        setSupportActionBar(questionBinding.toolbar);
        bindData();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        questionBinding.container.setAdapter(mSectionsPagerAdapter);

    }

    private void bindData() {
        questionList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            questionList.add(new Question(i, rand.nextInt(3)));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_review) {
            showPopupListQuestion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopupListQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogListAnswerBinding dialogListAnswerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.dialog_list_answer, null, false);
        builder.setView(dialogListAnswerBinding.getRoot());

        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        dialogListAnswerBinding.rvlist.setLayoutManager(layoutManager);
        dialogListAnswerBinding.rvlist.setHasFixedSize(true);
        dialogListAnswerBinding.rvlist.setItemAnimator(new DefaultItemAnimator());

        DialogListQuestionAdapter listQuestionAdapter = new DialogListQuestionAdapter(
                this, new ArrayList<Question>());
        dialogListAnswerBinding.rvlist.setAdapter(listQuestionAdapter);
        listQuestionAdapter.addAll(questionList);

        dialogListAnswer = builder.create();
        dialogListAnswer.show();
    }

    public static class QuestionFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private FragmentQuestionBinding fragmentQuestionBinding;

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            fragmentQuestionBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_question, container, false);
            fragmentQuestionBinding.tvPassage.setText(Html.fromHtml("<div class=\"info-title\" style=\"overflow: auto; max-height: 200px;\">\n" +
                    "                                                                                                            <p><strong>HOW TRANSPORTATION AFFECTS OUR LIVES</strong></p>\n" +
                    "\n" +
                    "<p>Without transportation, our modern society could not exist. We would have no metals, no coal, and no oil (31)______ would we have any products made from these materials. Besides, we would have to (32) ______ most of our time raising food - and the food would be limited to the kinds that could grow in the climate and soil of our own neighborhoods.</p>\n" +
                    "\n" +
                    "<p>Transportation also affects our lives in (33) ______ ways. Transportation can speed a doctor to the sides of a sick person, even if the (34)______ lives on an isolated farm. It can take police to the scene of a crime within moments of being notified. Transportation enables teams of athletes to compete in national and international sports (35)______. In times of disasters, transportation can rush aid to persons in areas stricken by floods, famines, and earthquakes.</p>\n" +
                    "                                                                                                    </div>"));
            return fragmentQuestionBinding.getRoot();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return QuestionFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}

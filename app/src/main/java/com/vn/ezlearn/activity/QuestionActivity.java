package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
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
import com.vn.ezlearn.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends BaseActivity {

    public ActivityQuestionBinding questionBinding;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private AlertDialog dialogListAnswer;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionBinding = DataBindingUtil.setContentView(this, R.layout.activity_question);

        setSupportActionBar(questionBinding.toolbar);
        initUI();
        bindData();
        event();
    }

    private void event() {
        questionBinding.svQuestion.setOnTouchListener(new OnSwipeTouchListener(QuestionActivity.this) {
            public void onSwipeTop() {
//                Toast.makeText(QuestionActivity.this, "top", Toast.LENGTH_SHORT).show();
                questionBinding.svQuestion.smoothScrollTo(0,0);
            }

            public void onSwipeRight() {
//                Toast.makeText(QuestionActivity.this, "right", Toast.LENGTH_SHORT).show();

                if (questionBinding.container.getCurrentItem() > 0) {
                    questionBinding.container.setCurrentItem(questionBinding.container.getCurrentItem() - 1);
                }
            }

            public void onSwipeLeft() {
//                Toast.makeText(QuestionActivity.this, "left", Toast.LENGTH_SHORT).show();
                if (questionBinding.container.getCurrentItem() < questionList.size()) {
                    questionBinding.container.setCurrentItem(questionBinding.container.getCurrentItem() + 1);
                }
            }

            public void onSwipeBottom() {
//                Toast.makeText(QuestionActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                questionBinding.svQuestion.fullScroll(View.FOCUS_DOWN);
            }

        });
    }

    private void initUI() {
        setBackButtonToolbar();
        questionBinding.toolbar.setTitle("00:45:00");
    }

    private void bindData() {
        questionBinding.tvPart.setText("Part 10: Read the following passage and mark the letter A, B, C, or D on your answer sheet to indicate the correct word or phrase that best fits each of the numbered blanks");
        questionBinding.tvPassage.setText(Html.fromHtml("<p><strong>HOW TRANSPORTATION AFFECTS OUR LIVES</strong></p>\n" +
                "\n" +
                "<p>Without transportation, our modern society could not exist. We would have no metals, no coal, and no oil (31)______ would we have any products made from these materials. Besides, we would have to (32) ______ most of our time raising food - and the food would be limited to the kinds that could grow in the climate and soil of our own neighborhoods.</p>\n" +
                "\n" +
                "<p>Transportation also affects our lives in (33) ______ ways. Transportation can speed a doctor to the sides of a sick person, even if the (34)______ lives on an isolated farm. It can take police to the scene of a crime within moments of being notified. Transportation enables teams of athletes to compete in national and international sports (35)______. In times of disasters, transportation can rush aid to persons in areas stricken by floods, famines, and earthquakes.</p>"));
        questionList = new ArrayList<>();
        Random rand = new Random();
        int min = Question.TYPE_ANSWERED;
        int max = Question.TYPE_LATE;
        for (int i = 0; i < 25; i++) {
            questionList.add(new Question(i, rand.nextInt(max + 1 - min) + min));
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        questionBinding.container.setAdapter(mSectionsPagerAdapter);

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

        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        dialogListAnswerBinding.rvlist.setLayoutManager(layoutManager);
        dialogListAnswerBinding.rvlist.setHasFixedSize(true);
        dialogListAnswerBinding.rvlist.setItemAnimator(new DefaultItemAnimator());

        DialogListQuestionAdapter listQuestionAdapter = new DialogListQuestionAdapter(
                this, new ArrayList<Question>());
        dialogListAnswerBinding.rvlist.setAdapter(listQuestionAdapter);
        listQuestionAdapter.addAll(questionList);
        dialogListAnswerBinding.btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListAnswer.dismiss();
            }
        });
        dialogListAnswerBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListAnswer.dismiss();
            }
        });
        builder.setCancelable(false);
        dialogListAnswer = builder.create();
        dialogListAnswer.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static class QuestionFragment extends Fragment {
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
            fragmentQuestionBinding.rdAnswerA.setText(Html.fromHtml("either"));
            fragmentQuestionBinding.rdAnswerB.setText(Html.fromHtml("nor"));
            fragmentQuestionBinding.rdAnswerC.setText(Html.fromHtml("or"));
            fragmentQuestionBinding.rdAnswerD.setText(Html.fromHtml("both"));
            fragmentQuestionBinding.tvQuestion.setText("Question: " + position);
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
            return questionList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position + "";
        }
    }


//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (view.getId() == R.id.svQuestion) {
//
//            return true;
//        }
//        return false;
//    }

}

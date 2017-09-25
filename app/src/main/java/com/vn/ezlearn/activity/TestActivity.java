package com.vn.ezlearn.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.DialogListQuestionAdapter;
import com.vn.ezlearn.adapter.QuestionObjectAdapter;
import com.vn.ezlearn.databinding.ActivityTestBinding;
import com.vn.ezlearn.databinding.DialogListAnswerBinding;
import com.vn.ezlearn.interfaces.ChangeQuestionListener;
import com.vn.ezlearn.interfaces.OnCheckAnswerListener;
import com.vn.ezlearn.interfaces.OnClickQuestionPopupListener;
import com.vn.ezlearn.models.Question;
import com.vn.ezlearn.models.QuestionObject;
import com.vn.ezlearn.viewmodel.TestViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestActivity extends BaseActivity
        implements ChangeQuestionListener, OnCheckAnswerListener, OnClickQuestionPopupListener {
    private static final String FORMAT = "%02d:%02d:%02d";
    private ActivityTestBinding testBinding;
    private TestViewModel testViewModel;
    private QuestionObjectAdapter adapter;
    private List<QuestionObject> list;
    private List<Question> questionList;
    private AlertDialog dialogListAnswer;
    private CountDownTimer countDownTimer;
    private long time = 15 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        setSupportActionBar(testBinding.toolbar);

        initUI();
        bindData();
        countDown();
    }

    private void initUI() {
        setBackButtonToolbar();
        testViewModel = new TestViewModel(this, getString(R.string.app_name));
        testBinding.setTestViewModel(testViewModel);
    }

    private void bindData() {
        adapter = new QuestionObjectAdapter(this, new ArrayList<QuestionObject>(), this, this);
        testBinding.rvList.setAdapter(adapter);
        list = new ArrayList<>();
        questionList = new ArrayList<>();
        questionList.add(new Question(1, Question.TYPE_UNANSWER,
                "Part 1: Mark the letter A, B, C, or D on your answer sheet to indicate the word whose underlined part differs from the other three in pronunciation in each of the following questions.",
                null, null));
        questionList.add(new Question(2, Question.TYPE_UNANSWER,
                "Part 3: Mark the letter A, B, C, or D on your answer sheet to indicate the underlined part that needs correction in each of the following questions.",
                null,
                "I (A) <u>found</u> it (B) <u>is necessary</u> for you to (C) <u>come here</u> (D) <u>on time</u>."));
        questionList.add(new Question(3, Question.TYPE_UNANSWER,
                "Part 10: Read the following passage and mark the letter A, B, C, or D on your answer sheet to indicate the correct word or phrase that best fits each of the numbered blanks ",
                "<p><strong>HOW TRANSPORTATION AFFECTS OUR LIVES</strong></p><p>Without transportation, our modern society could not exist. We would have no metals, no coal, and no oil (31)______ would we have any products made from these materials. Besides, we would have to (32) ______ most of our time raising food - and the food would be limited to the kinds that could grow in the climate and soil of our own neighborhoods.</p><p>Transportation also affects our lives in (33) ______ ways. Transportation can speed a doctor to the sides of a sick person, even if the (34)______ lives on an isolated farm. It can take police to the scene of a crime within moments of being notified. Transportation enables teams of athletes to compete in national and international sports (35)______. In times of disasters, transportation can rush aid to persons in areas stricken by floods, famines, and earthquakes.</p>",
                null));
        list.add(new QuestionObject(questionList));
        list.add(0, new QuestionObject(questionList.get(0).part));

        adapter.addAll(list);
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
                this, new ArrayList<Question>(), this);
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
    public void onChange(int position) {
        if (questionList != null && questionList.size() > 0) {
            if (list != null && list.size() > 0) {
                adapter.setData(0, new QuestionObject(questionList.get(position).part));
            }
        }
    }

    @Override
    public void OnCheckAnswer(int position) {
        if (questionList != null && questionList.size() > position) {
            Question question = questionList.get(position);
            question.type = Question.TYPE_ANSWERED;
            questionList.set(position, question);
        }

    }

    @Override
    public void onNeedReview(int position) {
        if (questionList != null && questionList.size() > position) {
            Question question = questionList.get(position);
            question.type = Question.TYPE_LATE;
            questionList.set(position, question);
        }
    }

    @Override
    public void onClick(int position) {
        if (dialogListAnswer.isShowing()) {
            dialogListAnswer.dismiss();
            adapter.onMoveQuestion(position);
        }
    }

    private void countDown() {
        countDownTimer = new CountDownTimer(time, 1000) { // adjust the milli seconds here

            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                testViewModel.setTitle(
                        "" + String.format(
                                FORMAT,
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                        - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                        - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                testViewModel.setTitle(getString(R.string.hetGio));
            }
        };
        countDownTimer.start();

    }
}

package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.vn.ezlearn.model.Question;
import com.vn.ezlearn.model.QuestionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends BaseActivity {
    private ActivityTestBinding testBinding;
    private QuestionObjectAdapter adapter;
    private List<QuestionObject> list;
    private List<Question> questionList;
    private AlertDialog dialogListAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        initUI();
        bindData();
    }

    private void initUI() {
        setBackButtonToolbar();
        testBinding.toolbar.setTitle("00:45:00");
    }

    private void bindData() {
        adapter = new QuestionObjectAdapter(this, new ArrayList<QuestionObject>());
        testBinding.rvList.setAdapter(adapter);
        list = new ArrayList<>();
        questionList = new ArrayList<>();
//        list.add(new QuestionObject());
        list.add(new QuestionObject("Part 10: Read the following passage and mark the letter A, B, C, or D on your answer sheet to indicate the correct word or phrase that best fits each of the numbered blanks"));
        Random rand = new Random();
        int min = Question.TYPE_ANSWERED;
        int max = Question.TYPE_LATE;
        for (int i = 0; i < 25; i++) {
            questionList.add(new Question(i, rand.nextInt(max + 1 - min) + min));
        }
        list.add(new QuestionObject(questionList));
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
}

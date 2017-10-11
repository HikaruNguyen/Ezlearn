package com.vn.ezlearn.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.ActivityTestBinding;
import com.vn.ezlearn.databinding.DialogListAnswerBinding;
import com.vn.ezlearn.interfaces.ChangeQuestionListener;
import com.vn.ezlearn.interfaces.OnCheckAnswerListener;
import com.vn.ezlearn.interfaces.OnClickQuestionPopupListener;
import com.vn.ezlearn.modelresult.QuestionResult;
import com.vn.ezlearn.models.Content;
import com.vn.ezlearn.models.MyContent;
import com.vn.ezlearn.models.Question;
import com.vn.ezlearn.models.QuestionObject;
import com.vn.ezlearn.models.Reading;
import com.vn.ezlearn.viewmodel.TestViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity
        implements ChangeQuestionListener, OnCheckAnswerListener, OnClickQuestionPopupListener {
    public static final String KEY_ID = "id";
    private static final String FORMAT = "%02d:%02d:%02d";
    private ActivityTestBinding testBinding;
    private TestViewModel testViewModel;
    private QuestionObjectAdapter adapter;
    private List<QuestionObject> list;
    private List<MyContent> contentList;
    private AlertDialog dialogListAnswer;
    private CountDownTimer countDownTimer;
    private long time = 15 * 60 * 1000;

    private EzlearnService apiService;
    private Subscription mSubscription;
    private QuestionResult mQuestionResult;
    private int id;

    private boolean isAttach = true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        setSupportActionBar(testBinding.toolbar);
        getIntentData();
        initUI();
        bindData();
        event();
    }

    private void event() {
        testBinding.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onNextButton();
            }
        });

        testBinding.imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onPrevButton();
            }
        });
    }

    private void getIntentData() {
        id = getIntent().getIntExtra(KEY_ID, 0);
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
        contentList = new ArrayList<>();
        callDataApi();
    }

    private void callDataApi() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false);
        apiService = MyApplication.with(this).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getContentExam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QuestionResult>() {
                    @Override
                    public void onCompleted() {
                        if (mQuestionResult.success && mQuestionResult.data != null
                                && mQuestionResult.data.data != null
                                && mQuestionResult.data.data.size() > 0) {
                            for (Question question : mQuestionResult.data.data) {
                                if (question != null) {
                                    if (question.type == Question.TYPE_QUESTION) {
                                        if (question.question != null
                                                && question.question.size() > 0) {
                                            for (Content content : question.question) {
                                                MyContent myContent = new MyContent(
                                                        question.region, question.type, content);
                                                contentList.add(myContent);
                                            }
                                        }
                                    } else if (question.type == Question.TYPE_READING) {
                                        if (question.reading != null
                                                && question.reading.size() > 0) {
                                            for (Reading reading : question.reading) {
                                                for (Content content : reading.questions) {
                                                    MyContent myContent = new MyContent(
                                                            question.region, question.type, content,
                                                            reading.content);
                                                    contentList.add(myContent);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            list.add(new QuestionObject(contentList));
                            list.add(0, new QuestionObject(contentList.get(0).region.region_code
                                    + " " + contentList.get(0).region.description));
                            testViewModel.updatePosition(0, contentList.size());
                            adapter.addAll(list);
                            countDown();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttach && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(QuestionResult questionResult) {
                        if (isAttach && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (questionResult != null) {
                            mQuestionResult = questionResult;
                        }
                    }
                });
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
                this, new ArrayList<MyContent>(), this);
        dialogListAnswerBinding.rvlist.setAdapter(listQuestionAdapter);
        listQuestionAdapter.addAll(contentList);
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
        if (contentList != null && contentList.size() > 0) {
            if (list != null && list.size() > 0) {
                String part = contentList.get(position).region.region_code
                        + " " + contentList.get(0).region.description;
                if (!adapter.getItembyPostion(0).part.equals(part)) {
                    adapter.setData(0, new QuestionObject(contentList.get(position).region.region_code
                            + " " + contentList.get(0).region.description));
                }
            }
        }
        testViewModel.updatePosition(position, contentList.size());
    }

    @Override
    public void OnCheckAnswer(int position) {
        if (contentList != null && contentList.size() > position) {
            MyContent content = contentList.get(position);
            content.typeQuestion = MyContent.TYPE_ANSWERED;
            contentList.set(position, content);
        }

    }

    @Override
    public void onNeedReview(int position) {
        if (contentList != null && contentList.size() > position) {
            MyContent content = contentList.get(position);
            content.typeQuestion = MyContent.TYPE_LATE;
            contentList.set(position, content);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAttach = false;
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }

}

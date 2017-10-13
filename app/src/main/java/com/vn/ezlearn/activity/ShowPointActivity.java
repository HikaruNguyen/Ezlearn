package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.DialogListQuestionAdapter;
import com.vn.ezlearn.databinding.ActivityShowPointBinding;
import com.vn.ezlearn.models.MyContent;
import com.vn.ezlearn.utils.QuestionUtils;
import com.vn.ezlearn.viewmodel.ShowPointViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowPointActivity extends AppCompatActivity {
    private static final String TAG = ShowPointActivity.class.getSimpleName();
    public static final int KEY_REQUEST = 22;
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_HOURS = "KEY_HOURS";
    public static final String KEY_MINUTES = "KEY_MINUTES";
    public static final String KEY_SECONDS = "KEY_SECONDS";
    private ActivityShowPointBinding showPointBinding;
    private ShowPointViewModel showPointViewModel;
    private float point;
    private int numAnswerCorrect;
    private int numAnswerNoCorrect;
    private int numNoAnswer;

    private List<MyContent> myContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPointBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_point);
        getData();
        setProgressPoint();
        bindListQuestion();
        event();
    }

    private void event() {
        showPointBinding.btnReviewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void bindListQuestion() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        showPointBinding.rvQuestion.setLayoutManager(layoutManager);
        showPointBinding.rvQuestion.setHasFixedSize(true);
        showPointBinding.rvQuestion.setItemAnimator(new DefaultItemAnimator());

        DialogListQuestionAdapter listQuestionAdapter = new DialogListQuestionAdapter(
                this, new ArrayList<MyContent>());
        showPointBinding.rvQuestion.setAdapter(listQuestionAdapter);
        listQuestionAdapter.addAll(myContentList);
        showPointBinding.scrollView.fullScroll(ScrollView.FOCUS_UP);
    }


    private void getData() {
        String name = getIntent().getStringExtra(KEY_NAME);
        int hours = getIntent().getIntExtra(KEY_HOURS, 0);
        int minutes = getIntent().getIntExtra(KEY_MINUTES, 0);
        int seconds = getIntent().getIntExtra(KEY_SECONDS, 0);
        myContentList = QuestionUtils.getInstance().getMyContentList();
        for (MyContent myContent : myContentList) {
            if (myContent.typeQuestion == MyContent.TYPE_NO_ANSWER) {
                numNoAnswer++;
            } else {
                if (myContent.isCorrect) {
                    point += myContent.point;
                    numAnswerCorrect++;
                }
            }
        }
        numAnswerNoCorrect = myContentList.size() - numNoAnswer - numAnswerCorrect;
        showPointViewModel = new ShowPointViewModel(this, point, numAnswerCorrect,
                numAnswerNoCorrect, numNoAnswer, hours, minutes, seconds, name);
        showPointBinding.setShowPointViewModel(showPointViewModel);
    }

    int widthLinePoint;
    int widthPoint;
    int widthRlPoint = 0;


    private void setProgressPoint() {

        showPointBinding.linePoint.post(new Runnable() {
            @Override
            public void run() {
                widthLinePoint = showPointBinding.linePoint.getWidth();

                widthPoint = (int) (widthLinePoint / 10 * point);
                Log.d(TAG, "widthLinePoint: " + widthLinePoint + " " + widthPoint);


                showPointBinding.lnPoint.post(new Runnable() {
                    @Override
                    public void run() {
                        widthRlPoint = showPointBinding.lnPoint.getWidth();
                        LinearLayout.LayoutParams layoutParams
                                = new LinearLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        int marginLeftReal = widthPoint - (widthRlPoint / 2);
                        Log.d(TAG, "marginLeftReal: " + marginLeftReal + " " + widthRlPoint);
                        if (marginLeftReal > 0) {

                            if (widthPoint + (widthRlPoint / 2) > widthLinePoint) {
//                                layoutParams.setMargins(widthLinePoint - widthRlPoint, 0, 0, 0);
//                                Log.d(TAG, "marginOverRight: " + widthLinePoint + " "
//                                        + (widthPoint + (widthRlPoint / 2)) + " "
//                                        + (widthLinePoint - widthRlPoint) + " " + widthRlPoint);
                                layoutParams.gravity = Gravity.RIGHT;
                            } else {
                                layoutParams.setMargins(marginLeftReal, 0, 0, 0);
                            }
                        } else {
                            layoutParams.setMargins(0, 0, 0, 0);
                        }

                        if (point <= 0) {
                            showPointBinding.lnPoint.setBackgroundResource(
                                    R.drawable.bg_point_left);
                        } else if (point >= 10) {
                            showPointBinding.lnPoint.setBackgroundResource(
                                    R.drawable.bg_point_right);
                        } else {
                            showPointBinding.lnPoint.setBackgroundResource(
                                    R.drawable.bg_point_center);
                        }
                        showPointBinding.lnPoint.setLayoutParams(layoutParams);

                        showPointBinding.ivTriangle.post(new Runnable() {
                            @Override
                            public void run() {
                                int widthIv = showPointBinding.ivTriangle.getWidth();
                                int heightIv = showPointBinding.ivTriangle.getHeight();
                                LinearLayout.LayoutParams lpTriangle
                                        = new LinearLayout.LayoutParams(widthIv, heightIv);
                                Log.d(TAG, "lpTriangle: " + widthIv + " " + (widthPoint - widthIv / 2));
                                lpTriangle.setMargins((widthPoint - widthIv / 2), 0, 0, 0);
                                showPointBinding.ivTriangle.setLayoutParams(lpTriangle);
                            }
                        });


                    }
                });
            }
        });
    }
}

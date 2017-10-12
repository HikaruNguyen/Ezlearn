package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.ActivityShowPointBinding;
import com.vn.ezlearn.viewmodel.ShowPointViewModel;

public class ShowPointActivity extends AppCompatActivity {
    private static final String TAG = ShowPointActivity.class.getSimpleName();
    public static final String KEY_POINT = "point";
    private ActivityShowPointBinding showPointBinding;
    private ShowPointViewModel showPointViewModel;
    private float point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPointBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_point);
        getIntentData();
        setProgressPoint();
    }


    private void getIntentData() {
        point = getIntent().getFloatExtra(KEY_POINT, 0);
        point = 9.75f;
        showPointViewModel = new ShowPointViewModel(this, point);
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

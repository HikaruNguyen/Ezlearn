package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.vn.ezlearn.activity.QuestionActivity;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class HomeViewModel extends BaseObservable {
    private Context context;

    public HomeViewModel(Context context) {
        this.context = context;
    }

    public void onClick(View view) {
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }
}

package com.vn.ezlearn.viewmodel;

import android.app.Activity;
import android.view.View;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class QuestionViewModel extends BaseViewModel {

    public QuestionViewModel(Activity activity, String title) {
        super(activity, title);
        visiableError = View.GONE;
    }
}

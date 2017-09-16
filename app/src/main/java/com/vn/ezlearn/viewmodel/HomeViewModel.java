package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class HomeViewModel extends BaseObservable {
    private Context context;

    public HomeViewModel(Context context) {
        this.context = context;
    }
}

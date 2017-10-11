package com.vn.ezlearn.viewmodel;

import android.app.Activity;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 25/09/2017.
 */

public class TestViewModel extends BaseViewModel {
    public ObservableInt visiableNext;
    public ObservableInt visiablePre;

    public TestViewModel(Activity activity, String title) {
        super(activity, title);
        visiableNext = new ObservableInt(View.GONE);
        visiablePre = new ObservableInt(View.GONE);
    }

    public void updatePosition(int position, int size) {
        visiableNext.set(position + 1 >= size ? View.GONE : View.VISIBLE);
        visiablePre.set(position <= 0 ? View.GONE : View.VISIBLE);
    }
}

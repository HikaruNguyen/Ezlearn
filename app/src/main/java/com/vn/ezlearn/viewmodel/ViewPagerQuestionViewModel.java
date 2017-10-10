package com.vn.ezlearn.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by Windows 10 Version 2 on 10/10/2017.
 */

public class ViewPagerQuestionViewModel extends BaseObservable {
    public ObservableInt visiableNext;
    public ObservableInt visiablePre;
    private int size;

    public ViewPagerQuestionViewModel(int size) {
        int position = 0;
        this.size = size;
        visiableNext = new ObservableInt(position + 1 >= size ? View.GONE : View.VISIBLE);
        visiablePre = new ObservableInt(View.GONE);
    }

    public void updatePosition(int position) {
        visiableNext.set(position + 1 >= size ? View.GONE : View.VISIBLE);
        visiablePre.set(position <= 0 ? View.GONE : View.VISIBLE);
    }
}

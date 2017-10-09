package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/10/2017.
 */

public class MainViewModel extends BaseObservable {
    public Context context;
    public ObservableInt visiableTabBar;

    public MainViewModel(Context context) {
        this.context = context;
        visiableTabBar = new ObservableInt(View.GONE);
    }

    public void setVisiableTabBar(String id) {
        try {
            int numId = Integer.parseInt(id);
            if (numId > 0) {
                visiableTabBar.set(View.VISIBLE);
            } else {
                visiableTabBar.set(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            visiableTabBar.set(View.GONE);
        }
    }
}

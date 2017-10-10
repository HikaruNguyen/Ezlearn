package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.vn.ezlearn.R;
import com.vn.ezlearn.config.AppConfig;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 09/10/2017.
 */

public class MainViewModel extends BaseObservable {
    public Context context;
    public ObservableInt visiableTabBar;
    public ObservableField<String> email;
    public ObservableField<String> name;
    public ObservableInt visiableProfile;

    public MainViewModel(Context context) {
        this.context = context;
        visiableTabBar = new ObservableInt(View.GONE);
        email = new ObservableField<>();
        name = new ObservableField<>();
        visiableProfile = new ObservableInt();
        updateProfile();
    }

    public void updateProfile() {
        if (!AppConfig.getInstance(context).getToken().isEmpty()) {
            name.set(AppConfig.getInstance(context).getName());
            email.set(AppConfig.getInstance(context).getEmail());
            visiableProfile.set(View.VISIBLE);
        } else {
            name.set(context.getString(R.string.login));
            visiableProfile.set(View.GONE);
        }
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

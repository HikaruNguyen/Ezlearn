package com.vn.ezlearn.activity;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.modelresult.CategoryResult;

import rx.Scheduler;

/**
 * Created by manhi on 4/1/2017.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private EzlearnService ezlearnService;
    private Scheduler defaultSubscribeScheduler;
    public int REALM_VERSION = 1;
    private CategoryResult categoryResult;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Subscribed to news topic");
    }

    public EzlearnService getEzlearnService() {
        if (ezlearnService == null) {
            ezlearnService = EzlearnService.Factory.create(this);
        }
        return ezlearnService;
    }

    public static MyApplication with(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public CategoryResult getCategoryResult() {
        return categoryResult;
    }

    public void setCategoryResult(CategoryResult categoryResult) {
        this.categoryResult = categoryResult;
    }
}

package com.vn.ezlearn.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vn.ezlearn.R;
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.ActivitySplashBinding;
import com.vn.ezlearn.modelresult.CategoryResult;
import com.vn.ezlearn.models.Category;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private ActivitySplashBinding splashBinding;
    private EzlearnService apiService;
    private Subscription mSubscription;
    private CategoryResult mCategoryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
//        new Thread(new Task()).start();
        loadDataCategory();
    }

    private void loadDataCategory() {
        apiService = MyApplication.with(this).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getCategory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                        if (mCategoryResult.success) {
                            if (mCategoryResult.data != null && mCategoryResult.data.size() > 0) {
                                for (Category category : mCategoryResult.data) {
                                    if (category != null && category.children != null
                                            && category.children.size() > 0) {
                                        for (Category categoryChild : category.children) {
                                            if (categoryChild != null
                                                    && categoryChild.children != null
                                                    && categoryChild.children.size() > 0) {
                                                category.levelChild = Category.LEVEL_3;
                                                category.typeMenu = Category.TYPE_PARENT;
                                            } else {
                                                category.levelChild = Category.LEVEL_2;
                                                category.typeMenu = Category.TYPE_NORMAL;
                                            }
                                        }
                                    } else {
                                        category.levelChild = Category.LEVEL_1;
                                        category.typeMenu = Category.TYPE_NORMAL;
                                    }
                                }
                            }

                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            MyApplication.with(SplashActivity.this).setCategoryResult(mCategoryResult);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        goToMain();
                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        if (categoryResult != null) {
                            mCategoryResult = categoryResult;
                        }
                    }
                });
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goToMain();
        }


    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }
}

package com.vn.ezlearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.vn.ezlearn.R;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.fragment.CustomSlide;

import agency.tango.materialintroscreen.animations.IViewTranslation;


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConfig.getInstance(this).isShowIntro()) {
            onFinish();
        } else {
            enableLastSlideAlphaExitTransition(true);

            getBackButtonTranslationWrapper()
                    .setEnterTranslation(new IViewTranslation() {
                        @Override
                        public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                            view.setAlpha(percentage);
                        }
                    });


            addSlide(CustomSlide.newInstance(
                    true,
                    getString(R.string.bank_exams_title),
                    getString(R.string.bank_exams_description),
                    R.color.first_slide_background,
                    R.color.transparent,
                    R.mipmap.clouds));
            addSlide(CustomSlide.newInstance(
                    true,
                    getString(R.string.route_title),
                    getString(R.string.route_description),
                    R.color.second_slide_background,
                    R.color.transparent,
                    R.mipmap.route));

            addSlide(CustomSlide.newInstance(
                    false,
                    getString(R.string.exams_title),
                    getString(R.string.exams_description),
                    R.color.third_slide_background,
                    R.color.transparent,
                    R.mipmap.quality));
        }

    }


    @Override
    public void onFinish() {
        super.onFinish();
        AppConfig.getInstance(this).setIsShowIntro(true);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
}

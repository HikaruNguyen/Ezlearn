package com.vn.ezlearn.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.ViewPagerAdapter;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.databinding.ActivityIntroBinding;
import com.vn.ezlearn.fragment.CustomSlide;
import com.vn.ezlearn.utils.ColorUtil;
import com.vn.ezlearn.viewmodel.IntroViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding introBinding;
    private IntroViewModel introViewModel;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList;
    private int positionViewPager = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        introViewModel = new IntroViewModel(this);
        introBinding.setIntroViewModel(introViewModel);
        if (!AppConfig.getInstance(this).isShowIntro()) {
            initViewPager();
        } else {
            AppConfig.getInstance(IntroActivity.this).setIsShowIntro(true);
            Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        event();
    }

    private void event() {
        introBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionViewPager = position;
                colorStatusBar(((CustomSlide) fragmentList.get(position)).backgroundColor);
                if (position < fragmentList.size() - 1) {
                    introViewModel.textNext.set(getString(R.string.next));
                } else {
                    introViewModel.textNext.set(getString(R.string.done));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        introBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionViewPager < fragmentList.size() - 1) {
                    introBinding.viewPager.setCurrentItem(positionViewPager + 1);
                } else {
                    AppConfig.getInstance(IntroActivity.this).setIsShowIntro(true);
                    Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        introBinding.tvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfig.getInstance(IntroActivity.this).setIsShowIntro(true);
                Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        addSlide(CustomSlide.newInstance(
                true,
                getString(R.string.bank_exams_title),
                getString(R.string.bank_exams_description),
                R.color.first_slide_background,
                Color.TRANSPARENT,
                R.mipmap.clouds));
        addSlide(CustomSlide.newInstance(
                true,
                getString(R.string.route_title),
                getString(R.string.route_description),
                R.color.second_slide_background,
                Color.TRANSPARENT,
                R.mipmap.route));

        addSlide(CustomSlide.newInstance(
                false,
                getString(R.string.exams_title),
                getString(R.string.exams_description),
                R.color.third_slide_background,
                Color.TRANSPARENT,
                R.mipmap.quality));
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        introBinding.viewPager.setAdapter(pagerAdapter);
        introBinding.indicator.setViewPager(introBinding.viewPager);
        colorStatusBar(R.color.first_slide_background);
    }

    private void addSlide(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void colorStatusBar(int color) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Color.parseColor(
                    ColorUtil.changeColorHSB(getResources().getString(color))));
        }
    }
}

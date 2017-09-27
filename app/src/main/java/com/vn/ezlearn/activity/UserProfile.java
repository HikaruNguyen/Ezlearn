package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.ViewPagerAdapter;
import com.vn.ezlearn.databinding.ActivityUserProfileBinding;
import com.vn.ezlearn.fragment.UserProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends BaseActivity {
    private ActivityUserProfileBinding userProfileBinding;
    private List<Fragment> fragmentList;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        userProfileBinding.navigation.setOnNavigationItemSelectedListener(
                mOnNavigationItemSelectedListener);
        setSupportActionBar(userProfileBinding.toolbar);
        setBackButtonToolbar();
        userProfileBinding.toolbar.setTitle("");
        bindData();
        event();

    }

    private void event() {
        userProfileBinding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percentage = ((float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange());
                if (percentage > 0.75) {
                    userProfileBinding.icAvatar.setAlpha(0f);
                } else if (percentage > 0.5) {
                    userProfileBinding.icAvatar.setAlpha(1 - percentage);
                } else {
                    userProfileBinding.icAvatar.setAlpha(1f);
                }
            }
        });

        userProfileBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        userProfileBinding.navigation.setSelectedItemId(R.id.navigation_profile);
                        break;
                    case 1:
                        userProfileBinding.navigation.setSelectedItemId(R.id.navigation_history_exam);
                        break;
                    case 2:
                        userProfileBinding.navigation.setSelectedItemId(R.id.navigation_history_topup);
                        break;
                    case 3:
                        userProfileBinding.navigation.setSelectedItemId(R.id.navigation_history_buy_package);
                        break;
                    default:
                        userProfileBinding.navigation.setSelectedItemId(R.id.navigation_profile);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new UserProfileFragment());
        fragmentList.add(new UserProfileFragment());
        fragmentList.add(new UserProfileFragment());
        fragmentList.add(new UserProfileFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        userProfileBinding.viewPager.setAdapter(viewPagerAdapter);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    userProfileBinding.viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_history_exam:
                    userProfileBinding.viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_history_topup:
                    userProfileBinding.viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_history_buy_package:
                    userProfileBinding.viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

}

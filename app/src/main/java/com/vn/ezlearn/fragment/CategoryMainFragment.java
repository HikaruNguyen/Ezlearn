package com.vn.ezlearn.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.ViewPagerAdapter;
import com.vn.ezlearn.databinding.FragmentCategoryMainBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryMainFragment extends Fragment {


    private static final String CATEGORY_ID = "CATEGORY_ID";
    private String categoryID;

    private FragmentCategoryMainBinding categoryMainBinding;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;

    public CategoryMainFragment() {
        // Required empty public constructor
    }

    public static CategoryMainFragment newInstance(String categoryID) {
        CategoryMainFragment fragment = new CategoryMainFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getString(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_category_main, container, false);
        categoryMainBinding.navigation.setOnNavigationItemSelectedListener(
                mOnNavigationItemSelectedListener);
        bindData();
        event();
        return categoryMainBinding.getRoot();
    }

    private void event() {
        categoryMainBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    categoryMainBinding.navigation.setSelectedItemId(R.id.navigation_home);
                } else if (position == 1) {
                    categoryMainBinding.navigation.setSelectedItemId(R.id.navigation_dashboard);
                } else if (position == 2) {
                    categoryMainBinding.navigation.setSelectedItemId(R.id.navigation_notifications);
                } else {
                    categoryMainBinding.navigation.setSelectedItemId(R.id.navigation_home);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(CategoryFragment.newInstance(CategoryFragment.TYPE_BAI_GIANG,
                Integer.parseInt(categoryID)));
        fragmentList.add(CategoryFragment.newInstance(CategoryFragment.TYPE_DE_THI,
                Integer.parseInt(categoryID)));
        fragmentList.add(CategoryFragment.newInstance(CategoryFragment.TYPE_LUYEN_TAP,
                Integer.parseInt(categoryID)));
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        categoryMainBinding.viewPager.setAdapter(viewPagerAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    categoryMainBinding.viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    categoryMainBinding.viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    categoryMainBinding.viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };
}

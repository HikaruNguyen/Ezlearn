package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.ActivityQuestionBinding;
import com.vn.ezlearn.fragment.QuestionFragment;
import com.vn.ezlearn.viewmodel.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends BaseActivity {
    private ActivityQuestionBinding questionBinding;
    private QuestionViewModel questionViewModel;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionBinding = DataBindingUtil.setContentView(this, R.layout.activity_question);
        questionViewModel = new QuestionViewModel(this, "");
        questionBinding.setQuestionViewModel(questionViewModel);
        getDataIntent();
        initUI();
        bindData();
    }

    private void getDataIntent() {

    }

    private void initUI() {
        setBackButtonToolbar();
        questionViewModel.setTitle("Grammar G153- Preposition (*Free)");
    }

    private void bindData() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            fragmentList.add(new QuestionFragment());
        }
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        questionBinding.viewPager.setAdapter(pagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList) {
            super(manager);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
//            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

}

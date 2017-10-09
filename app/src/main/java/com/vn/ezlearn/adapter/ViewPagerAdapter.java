package com.vn.ezlearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentListTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList) {
        super(manager);
        this.fragmentList = fragmentList;
    }

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList,
                            List<String> fragmentListTitle) {
        super(manager);
        this.fragmentList = fragmentList;
        this.fragmentListTitle = fragmentListTitle;
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
        if (fragmentListTitle != null && fragmentListTitle.size() > position) {
            return fragmentListTitle.get(position);
        }
        return "";
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
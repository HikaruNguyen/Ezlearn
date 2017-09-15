package com.vn.ezlearn.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.HomeAdapter;
import com.vn.ezlearn.databinding.FragmentHomeBinding;
import com.vn.ezlearn.model.Banner;
import com.vn.ezlearn.model.Category;
import com.vn.ezlearn.model.Exam;
import com.vn.ezlearn.model.HomeObject;
import com.vn.ezlearn.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {

    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private List<HomeObject> list;
    private List<Banner> bannerList;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(getActivity());
        homeBinding.setHomeViewModel(homeViewModel);
        initBanner();
        bindData();
        return homeBinding.getRoot();
    }


    private void bindData() {
        homeAdapter = new HomeAdapter(getActivity(), new ArrayList<HomeObject>());
        homeBinding.rvHome.setAdapter(homeAdapter);

        list = new ArrayList<>();
        list.add(new HomeObject(bannerList));
        list.add(new HomeObject(new Category()));
        list.add(new HomeObject(new Exam()));
        list.add(new HomeObject(new Exam()));
        list.add(new HomeObject(new Exam()));
        list.add(new HomeObject(new Category()));
        list.add(new HomeObject(new Exam()));
        list.add(new HomeObject(new Exam()));
        list.add(new HomeObject(new Exam()));

        homeAdapter.addAll(list);
    }

    private void initBanner() {
        bannerList = new ArrayList<>();
        bannerList.add(new Banner(1, R.mipmap.banner01));
        bannerList.add(new Banner(2, R.mipmap.banner02));
        bannerList.add(new Banner(3, R.mipmap.banner03));
    }

    private void initBanner1() {


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

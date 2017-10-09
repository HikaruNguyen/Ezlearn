package com.vn.ezlearn.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.MyApplication;
import com.vn.ezlearn.adapter.HomeAdapter;
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.FragmentHomeBinding;
import com.vn.ezlearn.modelresult.BannerResult;
import com.vn.ezlearn.modelresult.ExamsResult;
import com.vn.ezlearn.models.Banner;
import com.vn.ezlearn.models.HomeObject;
import com.vn.ezlearn.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {

    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private List<HomeObject> list;
    private List<Banner> bannerList;

    private EzlearnService apiService;
    private Subscription mSubscription;

    private BannerResult mBannerResult;
    private ExamsResult mExamsResult;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(getActivity());
        homeBinding.setHomeViewModel(homeViewModel);
        initList();
        initBanner();
        bindData();
        return homeBinding.getRoot();
    }

    private void initList() {
        list = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), new ArrayList<HomeObject>());
        homeBinding.rvHome.setAdapter(homeAdapter);
    }


    private void bindData() {

        getListExamFree(1);
//        list.add(new HomeObject(bannerList));
//        list.add(new HomeObject(new CategoryFake()));
//        list.add(new HomeObject(new Exam()));
//        list.add(new HomeObject(new Exam()));
//        list.add(new HomeObject(new Exam()));
//        list.add(new HomeObject(new CategoryFake()));
//        list.add(new HomeObject(new Exam()));
//        list.add(new HomeObject(new Exam()));
//        list.add(new HomeObject(new Exam()));


    }

    private void getListExamFree(int page) {
        apiService = MyApplication.with(getActivity()).getEzlearnService();
        mSubscription = apiService.getListFreeExams(page, 3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExamsResult>() {
                    @Override
                    public void onCompleted() {
                        if (mExamsResult.success && mExamsResult.data != null
                                && mExamsResult.data.list != null
                                && mExamsResult.data.list.size() > 0) {
                            homeAdapter.add(new HomeObject(getString(R.string.tryExam)));
                            for (int i = 0; i < mExamsResult.data.list.size(); i++) {
                                homeAdapter.add(new HomeObject(mExamsResult.data.list.get(i)));
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ExamsResult examsResult) {
                        if (examsResult != null) {
                            mExamsResult = examsResult;
                        }
                    }
                });
    }

    private void initBanner() {
        bannerList = new ArrayList<>();
        apiService = MyApplication.with(getActivity()).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getBanners()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerResult>() {
                    @Override
                    public void onCompleted() {
                        if (mBannerResult.success && mBannerResult.data != null
                                && mBannerResult.data.size() > 0) {
                            for (int i = 0; i < mBannerResult.data.size(); i++) {
                                bannerList.add(new Banner(mBannerResult.data.get(i)));
                            }
                            homeAdapter.add(0, new HomeObject(bannerList));
                            homeBinding.rvHome.smoothScrollToPosition(0);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BannerResult bannerResult) {
                        if (bannerResult != null) {
                            mBannerResult = bannerResult;
                        }
                    }
                });
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }
}

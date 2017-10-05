package com.vn.ezlearn.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.MyApplication;
import com.vn.ezlearn.adapter.ExamsAdapter;
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.FragmentCategoryBinding;
import com.vn.ezlearn.modelresult.ExamsResult;
import com.vn.ezlearn.models.Exam;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryFragment extends Fragment {

    public static final int TYPE_BAI_GIANG = 1;
    public static final int TYPE_DE_THI = 2;
    public static final int TYPE_LUYEN_TAP = 3;
    private static final String TYPE_CATEGORY = "TYPE_CATEGORY";
    private static final String CATEGORY_ID = "CATEGORY_ID";

    private FragmentCategoryBinding categoryBinding;
    private EzlearnService apiService;
    private Subscription mSubscription;

    private int type_category;
    private int category_id;
    private ExamsResult mExamsResult;
    private ExamsAdapter adapter;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(int type_category, int category_id) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_CATEGORY, type_category);
        args.putInt(CATEGORY_ID, category_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type_category = getArguments().getInt(TYPE_CATEGORY);
            category_id = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_category, container, false);
        bindData();
        return categoryBinding.getRoot();
    }

    private void bindData() {
        adapter = new ExamsAdapter(getActivity(), new ArrayList<Exam>());
        categoryBinding.rvListExam.setAdapter(adapter);
        getDataApi(1);
    }

    private void getDataApi(int page) {
        apiService = MyApplication.with(getActivity()).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getListExams(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExamsResult>() {
                    @Override
                    public void onCompleted() {
                        if (mExamsResult.success && mExamsResult.data != null
                                && mExamsResult.data.list != null
                                && mExamsResult.data.list.size() > 0) {
                            adapter.addAll(mExamsResult.data.list);
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

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }
}

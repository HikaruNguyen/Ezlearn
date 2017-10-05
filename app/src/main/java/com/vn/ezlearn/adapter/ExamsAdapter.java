package com.vn.ezlearn.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.BR;
import com.vn.ezlearn.R;
import com.vn.ezlearn.models.Exam;
import com.vn.ezlearn.viewmodel.ItemExamViewModel;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

public class ExamsAdapter extends BaseRecyclerAdapter<Exam,
        ExamsAdapter.ViewHolder> {

    public ExamsAdapter(Context context, List<Exam> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setVariable(BR.itemExamViewModel,
                new ItemExamViewModel(mContext, list.get(position)));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_home_exams, parent, false);

        return new ViewHolder(binding);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mViewDataBinding;

        ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }
}

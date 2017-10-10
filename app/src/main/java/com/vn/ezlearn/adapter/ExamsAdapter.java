package com.vn.ezlearn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.TestActivity;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.databinding.ItemHomeExamsBinding;
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
        ItemHomeExamsBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setItemExamViewModel(new ItemExamViewModel(mContext, list.get(position)));
        viewDataBinding.lnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppConfig.getInstance(mContext).getToken().isEmpty()) {
                    Intent intent = new Intent(mContext, TestActivity.class);
                    intent.putExtra(TestActivity.KEY_ID, list.get(position).id);
                    mContext.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(mContext.getString(R.string.needLogin));
                    builder.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.show();
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeExamsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_home_exams, parent, false);

        return new ViewHolder(binding);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemHomeExamsBinding mViewDataBinding;

        ViewHolder(ItemHomeExamsBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, TestActivity.class);
//                    intent.putExtra(TestActivity.KEY_ID, list.get(getAdapterPosition()).id);
//                    mContext.startActivity(intent);
//                }
//            });
        }

        ItemHomeExamsBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }
}

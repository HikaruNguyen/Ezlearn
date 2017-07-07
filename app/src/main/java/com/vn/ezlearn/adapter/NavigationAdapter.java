package com.vn.ezlearn.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vn.ezlearn.BR;
import com.vn.ezlearn.R;
import com.vn.ezlearn.model.ItemMenu;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class NavigationAdapter extends BaseRecyclerAdapter<ItemMenu, NavigationAdapter.ViewHolder> {

    public NavigationAdapter(Context context, List<ItemMenu> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setVariable(BR.itemMenuViewModel, list.get(position));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_menu, parent, false);
        return new ViewHolder(binding);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mViewDataBinding;

        ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;

        }
    }
}

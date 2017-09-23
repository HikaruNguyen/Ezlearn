package com.vn.ezlearn.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.vn.ezlearn.R;
import com.vn.ezlearn.models.ItemMenu;
import com.vn.ezlearn.models.ItemMenuChild;
import com.vn.ezlearn.interfaces.NavigationItemSelected;
import com.vn.ezlearn.widgets.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class NavigationAdapter extends BaseRecyclerAdapter<ItemMenu, NavigationAdapter.ViewHolder> {

    private final List<ItemMenu> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private NavigationItemSelected navigationItemSelected;

    public NavigationAdapter(Context context, List<ItemMenu> list,
                             NavigationItemSelected navigationItemSelected) {
        super(context, list);
        this.data = list;
        this.navigationItemSelected = navigationItemSelected;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        if (viewType == ItemMenu.TYPE_NORMAL) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_menu_normal, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_menu, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemMenu item = data.get(position);
        holder.setIsRecyclable(false);
        holder.tvName.setText(item.name);

        if (getItemViewType(position) != ItemMenu.TYPE_NORMAL) {
            NavigationChildAdapter childAdapter = new NavigationChildAdapter(
                    context, new ArrayList<ItemMenuChild>(), navigationItemSelected);
            holder.rvItemMenuChild.setAdapter(childAdapter);
            childAdapter.addAll(item.menuChildList);
            holder.expandableLayout.setInRecyclerView(true);
            holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            holder.expandableLayout.setExpanded(expandState.get(position));
            holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                    expandState.put(position, true);
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                    expandState.put(position, false);
                }
            });

            holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
            holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    onClickButton(holder.expandableLayout);
                }
            });
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButton(holder.expandableLayout);
                }
            });
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigationItemSelected.onSelected(item.name, item.id);
                }
            });
        }

    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public RelativeLayout buttonLayout;
        public CRecyclerView rvItemMenuChild;
        public ExpandableLinearLayout expandableLayout;
        public RelativeLayout item;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            buttonLayout = v.findViewById(R.id.button);
            item = v.findViewById(R.id.item);
            expandableLayout = v.findViewById(R.id.expandableLayout);
            rvItemMenuChild = v.findViewById(R.id.rvItemMenuChild);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
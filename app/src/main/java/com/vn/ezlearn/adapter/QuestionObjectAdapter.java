package com.vn.ezlearn.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.TestActivity;
import com.vn.ezlearn.fragment.QuestionFragment;
import com.vn.ezlearn.model.Question;
import com.vn.ezlearn.model.QuestionObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class QuestionObjectAdapter extends BaseRecyclerAdapter<QuestionObject, QuestionObjectAdapter.ViewHolder> {
    private final List<QuestionObject> data;
    private Activity activity;
    private List<Fragment> questionFragments;
    private ViewPagerAdapter viewPagerAdapter;

    public QuestionObjectAdapter(Activity activity, List<QuestionObject> list) {
        super(activity, list);
        this.activity = activity;
        this.data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
//        this.context = parent.getContext();
        if (viewType == QuestionObject.TYPE_PART) {
            return new ViewHolder(LayoutInflater.from(activity)
                    .inflate(R.layout.item_question_part, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(activity)
                    .inflate(R.layout.item_question_viewpager, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QuestionObject item = data.get(position);

        if (getItemViewType(position) == QuestionObject.TYPE_PART) {
        } else if (getItemViewType(position) == QuestionObject.TYPE_VIEWPAGER) {
            List<Question> questionList = item.list;
            questionFragments = new ArrayList<>();
            if (questionList != null) {
                for (int i = 0; i < questionList.size(); i++) {
                    questionFragments.add(QuestionFragment.newInstance(i+1));
                }
            }
            viewPagerAdapter = new ViewPagerAdapter(
                    ((TestActivity) activity).getSupportFragmentManager(), questionFragments);
            holder.viewPager.setAdapter(viewPagerAdapter);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager;

        public ViewHolder(View v) {
            super(v);
            viewPager = v.findViewById(R.id.container);
        }
    }
}
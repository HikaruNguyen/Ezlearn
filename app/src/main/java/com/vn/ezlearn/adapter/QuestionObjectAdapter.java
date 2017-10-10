package com.vn.ezlearn.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.TestActivity;
import com.vn.ezlearn.databinding.ItemQuestionPartBinding;
import com.vn.ezlearn.databinding.ItemQuestionViewpagerBinding;
import com.vn.ezlearn.fragment.QuestionFragment;
import com.vn.ezlearn.interfaces.ChangeQuestionListener;
import com.vn.ezlearn.interfaces.OnCheckAnswerListener;
import com.vn.ezlearn.models.Content;
import com.vn.ezlearn.models.QuestionObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class QuestionObjectAdapter
        extends BaseRecyclerAdapter<QuestionObject, QuestionObjectAdapter.ViewHolder> {
    private final List<QuestionObject> data;
    private Activity activity;
    private List<Fragment> questionFragments;
    private ViewPagerAdapter viewPagerAdapter;
    private ChangeQuestionListener changeQuestionListener;
    private OnCheckAnswerListener onCheckAnswerListener;
    private ItemQuestionPartBinding itemQuestionPartBinding;
    private ItemQuestionViewpagerBinding itemQuestionViewpagerBinding;

    public QuestionObjectAdapter(Activity activity, List<QuestionObject> list,
                                 ChangeQuestionListener changeQuestionListener,
                                 OnCheckAnswerListener onCheckAnswerListener) {
        super(activity, list);
        this.activity = activity;
        this.data = list;
        this.changeQuestionListener = changeQuestionListener;
        this.onCheckAnswerListener = onCheckAnswerListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
//        this.context = parent.getContext();
        if (viewType == QuestionObject.TYPE_PART) {
            ItemQuestionPartBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_question_part, parent, false);
            return new ViewHolder(binding);
        } else {
//            return new ViewHolder(LayoutInflater.from(activity)
//                    .inflate(R.layout.item_question_viewpager, parent, false));
            ItemQuestionViewpagerBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_question_viewpager, parent, false);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QuestionObject item = data.get(position);

        if (getItemViewType(position) == QuestionObject.TYPE_PART) {
//            holder.tvPart.setText(item.part);
            itemQuestionPartBinding = holder.getItemQuestionPartBinding();
            itemQuestionPartBinding.tvPart.setText(item.part);
        } else if (getItemViewType(position) == QuestionObject.TYPE_VIEWPAGER) {
            itemQuestionViewpagerBinding =
                    holder.getItemQuestionViewpagerBinding();
            List<Content> contentList = item.list;
            questionFragments = new ArrayList<>();
            if (contentList != null) {
                for (int i = 0; i < contentList.size(); i++) {
                    QuestionFragment questionFragment =
                            QuestionFragment.newInstance(i, contentList.size());
                    questionFragment.setQuestion(contentList.get(i), onCheckAnswerListener);
                    questionFragments.add(questionFragment);
                }
            }
            viewPagerAdapter = new ViewPagerAdapter(
                    ((TestActivity) activity).getSupportFragmentManager(), questionFragments);
            itemQuestionViewpagerBinding.container.setAdapter(viewPagerAdapter);

            itemQuestionViewpagerBinding.container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(
                        int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    changeQuestionListener.onChange(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
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

    public void onMoveQuestion(int position) {
        if (list != null && list.size() > 1) {
            if (list.get(1).list != null && list.get(1).list.size() > position) {
                itemQuestionViewpagerBinding.container.setCurrentItem(position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemQuestionPartBinding itemQuestionPartBinding;
        private ItemQuestionViewpagerBinding itemQuestionViewpagerBinding;

        public ViewHolder(ItemQuestionPartBinding itemQuestionPartBinding) {
            super(itemQuestionPartBinding.getRoot());
            this.itemQuestionPartBinding = itemQuestionPartBinding;
            this.itemQuestionPartBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        }
            );
        }

        public ViewHolder(ItemQuestionViewpagerBinding itemQuestionViewpagerBinding) {
            super(itemQuestionViewpagerBinding.getRoot());
            this.itemQuestionViewpagerBinding = itemQuestionViewpagerBinding;
            this.itemQuestionViewpagerBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        }
            );
        }

        ItemQuestionPartBinding getItemQuestionPartBinding() {
            return itemQuestionPartBinding;
        }

        ItemQuestionViewpagerBinding getItemQuestionViewpagerBinding() {
            return itemQuestionViewpagerBinding;
        }
    }
}
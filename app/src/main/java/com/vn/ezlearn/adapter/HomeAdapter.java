package com.vn.ezlearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.TestActivity;
import com.vn.ezlearn.model.HomeObject;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class HomeAdapter extends BaseRecyclerAdapter<HomeObject, HomeAdapter.ViewHolder> {
    private static final long SLIDE_DELAY = 5000;
    private final List<HomeObject> data;
    private Context context;
    private boolean isAddedSlide;

    public HomeAdapter(Context context, List<HomeObject> list) {
        super(context, list);
        this.data = list;
        isAddedSlide = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        if (viewType == HomeObject.TYPE_HEADER) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_home_header, parent, false));
        } else if (viewType == HomeObject.TYPE_EXAM) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_home_exams, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_home_slide, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HomeObject item = data.get(position);

        if (getItemViewType(position) == HomeObject.TYPE_SLIDE) {
            if (!isAddedSlide) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int width = displayMetrics.widthPixels;
                float height = width * 485 / 1366;
                holder.rlSlide.setLayoutParams(new LinearLayout.LayoutParams(width, (int) height));

                holder.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                holder.slider.setCustomIndicator(holder.pagerIndicator);
                holder.slider.setCustomAnimation(new DescriptionAnimation());
                holder.slider.setDuration(SLIDE_DELAY);

                for (int i = 0; i < item.bannerList.size(); i++) {
                    DefaultSliderView textSliderView = new DefaultSliderView(context);
                    textSliderView
                            .image(item.bannerList.get(i).imageDrawable)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {

                                }
                            });
                    holder.slider.addSlider(textSliderView);
                }
                isAddedSlide = true;
            }
        } else if (getItemViewType(position) == HomeObject.TYPE_EXAM) {
            holder.lnExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TestActivity.class);
                    context.startActivity(intent);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlSlide;
        private SliderLayout slider;
        private PagerIndicator pagerIndicator;
        private LinearLayout lnExam;

        public ViewHolder(View v) {
            super(v);
            rlSlide = v.findViewById(R.id.rlSlide);
            slider = v.findViewById(R.id.slider);
            pagerIndicator = v.findViewById(R.id.custom_indicator2);
            lnExam = v.findViewById(R.id.lnExam);
        }
    }
}
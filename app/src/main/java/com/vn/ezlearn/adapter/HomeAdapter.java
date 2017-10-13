package com.vn.ezlearn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.vn.ezlearn.R;
import com.vn.ezlearn.activity.TestActivity;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.databinding.ItemHomeExamsBinding;
import com.vn.ezlearn.databinding.ItemHomeHeaderBinding;
import com.vn.ezlearn.databinding.ItemHomeSlideBinding;
import com.vn.ezlearn.models.Banner;
import com.vn.ezlearn.models.HomeObject;
import com.vn.ezlearn.viewmodel.ItemExamViewModel;

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
            ItemHomeHeaderBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_home_header, parent, false);
            return new ViewHolder(binding);
        } else if (viewType == HomeObject.TYPE_EXAM) {
            ItemHomeExamsBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_home_exams, parent, false);
            return new ViewHolder(binding);
        } else {
            ItemHomeSlideBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_home_slide, parent, false);
            return new ViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HomeObject item = data.get(position);
        if (getItemViewType(position) == HomeObject.TYPE_SLIDE) {
            ItemHomeSlideBinding itemHomeSlideBinding = holder.itemHomeSlideBinding;
            if (!isAddedSlide) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int width = displayMetrics.widthPixels;
                float height = width * 485 / 1366;
                itemHomeSlideBinding.rlSlide.setLayoutParams(new LinearLayout.LayoutParams(width, (int) height));

                itemHomeSlideBinding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                itemHomeSlideBinding.slider.setCustomIndicator(itemHomeSlideBinding.customIndicator2);
                itemHomeSlideBinding.slider.setCustomAnimation(new DescriptionAnimation());
                itemHomeSlideBinding.slider.setDuration(SLIDE_DELAY);

                for (int i = 0; i < item.bannerList.size(); i++) {
                    DefaultSliderView textSliderView = new DefaultSliderView(context);
                    if (item.bannerList.get(i).type == Banner.TYPE_DRAWABLE) {
                        textSliderView
                                .image(item.bannerList.get(i).imageDrawable)
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {

                                    }
                                });
                    } else {
                        textSliderView
                                .image(item.bannerList.get(i).imageUrl)
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {

                                    }
                                });
                    }

                    itemHomeSlideBinding.slider.addSlider(textSliderView);
                }
                isAddedSlide = true;
            }
        } else if (getItemViewType(position) == HomeObject.TYPE_EXAM) {
            ItemHomeExamsBinding itemHomeExamsBinding = holder.itemHomeExamsBinding;
            ItemExamViewModel itemExamViewModel = new ItemExamViewModel(mContext, list.get(position).exam);
            itemHomeExamsBinding.setItemExamViewModel(itemExamViewModel);
            itemHomeExamsBinding.lnExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!AppConfig.getInstance(mContext).getToken().isEmpty()) {
                        Intent intent = new Intent(mContext, TestActivity.class);
                        intent.putExtra(TestActivity.KEY_ID, list.get(position).exam.id);
                        intent.putExtra(TestActivity.KEY_NAME, list.get(position).exam.subject_code);
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
        } else if (getItemViewType(position) == HomeObject.TYPE_HEADER) {
            ItemHomeHeaderBinding itemHomeHeaderBinding = holder.itemHomeHeaderBinding;
            itemHomeHeaderBinding.tvName.setText(list.get(position).header);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHomeSlideBinding itemHomeSlideBinding;
        private ItemHomeHeaderBinding itemHomeHeaderBinding;
        private ItemHomeExamsBinding itemHomeExamsBinding;

//        private RelativeLayout rlSlide;
//        private SliderLayout slider;
//        private PagerIndicator pagerIndicator;
//        private LinearLayout lnExam;

//        public ViewHolder(View v) {
//            super(v);
//            rlSlide = v.findViewById(R.id.rlSlide);
//            slider = v.findViewById(R.id.slider);
//            pagerIndicator = v.findViewById(R.id.custom_indicator2);
//            lnExam = v.findViewById(R.id.lnExam);
//        }

        public ViewHolder(ItemHomeSlideBinding itemHomeSlideBinding) {
            super(itemHomeSlideBinding.getRoot());
            this.itemHomeSlideBinding = itemHomeSlideBinding;
            this.itemHomeSlideBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        }
            );
        }

        public ViewHolder(ItemHomeHeaderBinding itemHomeHeaderBinding) {
            super(itemHomeHeaderBinding.getRoot());
            this.itemHomeHeaderBinding = itemHomeHeaderBinding;
            this.itemHomeHeaderBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        }
            );
        }

        public ViewHolder(ItemHomeExamsBinding itemHomeExamsBinding) {
            super(itemHomeExamsBinding.getRoot());
            this.itemHomeExamsBinding = itemHomeExamsBinding;
            this.itemHomeExamsBinding.executePendingBindings();
        }
    }
}
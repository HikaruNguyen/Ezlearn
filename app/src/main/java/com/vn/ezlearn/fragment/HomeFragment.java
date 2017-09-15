package com.vn.ezlearn.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.vn.ezlearn.databinding.FragmentHomeBinding;
import com.vn.ezlearn.viewmodel.HomeViewModel;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {
    private static final long SLIDE_DELAY = 5000;
    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(getActivity());
        homeBinding.setHomeViewModel(homeViewModel);
        initBanner();
        event();
        return homeBinding.getRoot();
    }

    private void event() {

    }

    private void initBanner() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        float height = width * 485 / 1366;
        homeBinding.rlSlide.setVisibility(View.VISIBLE);
        homeBinding.rlSlide.setLayoutParams(new LinearLayout.LayoutParams(width, (int) height));

        homeBinding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        homeBinding.slider.setCustomIndicator(homeBinding.customIndicator2);
        homeBinding.slider.setCustomAnimation(new DescriptionAnimation());
        homeBinding.slider.setDuration(SLIDE_DELAY);

        DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
        textSliderView
                .image(R.mipmap.banner01)
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);
        homeBinding.slider.addSlider(textSliderView);

        textSliderView = new DefaultSliderView(getActivity());
        textSliderView
                .image(R.mipmap.banner02)
                .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                .setOnSliderClickListener(this);
        homeBinding.slider.addSlider(textSliderView);

        textSliderView = new DefaultSliderView(getActivity());
        textSliderView
                .image(R.mipmap.banner03)
                .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                .setOnSliderClickListener(this);
        homeBinding.slider.addSlider(textSliderView);


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

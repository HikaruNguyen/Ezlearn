package com.vn.ezlearn.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.ezlearn.R;
import com.vn.ezlearn.databinding.FragmentSlideBinding;
import com.vn.ezlearn.viewmodel.CustomSlideViewModel;


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class CustomSlide extends Fragment {
    private static final String ARG_IS_CAN_MOVE = "is_can_move";
    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_BACKGROUND_COLOR = "background_color";
    private static final String ARG_BUTTON_COLOR = "button_color";
    private static final String ARG_IMAGE = "image";

    private boolean isCanMove;
    private String title;
    private String description;
    public int backgroundColor;
    private int buttonColor;
    private int image;

    private FragmentSlideBinding slideBinding;
    private CustomSlideViewModel customSlideViewModel;


    public static CustomSlide newInstance(boolean isCanMove, String title, String description,
                                          int backgroundColor, int buttonColor, int image) {
        CustomSlide fragment = new CustomSlide();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_CAN_MOVE, isCanMove);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_BACKGROUND_COLOR, backgroundColor);
        args.putInt(ARG_BUTTON_COLOR, buttonColor);
        args.putInt(ARG_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isCanMove = getArguments().getBoolean(ARG_IS_CAN_MOVE);
            title = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESCRIPTION);
            backgroundColor = getArguments().getInt(ARG_BACKGROUND_COLOR);
            buttonColor = getArguments().getInt(ARG_BUTTON_COLOR);
            image = getArguments().getInt(ARG_IMAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        slideBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_slide, container, false);
        customSlideViewModel = new CustomSlideViewModel(getActivity(), title, description, image,
                backgroundColor);
        slideBinding.setCustomSlideViewModel(customSlideViewModel);
        return slideBinding.getRoot();
    }
}
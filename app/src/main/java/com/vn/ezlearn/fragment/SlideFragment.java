package com.vn.ezlearn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vn.ezlearn.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agency.tango.materialintroscreen.parallax.ParallaxFragment;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class SlideFragment extends ParallaxFragment {
    private final static String BACKGROUND_COLOR = "background_color";
    private static final String BUTTONS_COLOR = "buttons_color";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String NEEDED_PERMISSIONS = "needed_permission";
    private static final String POSSIBLE_PERMISSIONS = "possible_permission";
    private static final String IMAGE = "image";
    private static final int PERMISSIONS_REQUEST_CODE = 15621;

    private int backgroundColor;
    private int buttonsColor;
    private int image;
    private String title;
    private String description;
    private String[] neededPermissions;
    private String[] possiblePermissions;

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    public static SlideFragment createInstance(SlideFragmentBuilder builder) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR, builder.backgroundColor);
        bundle.putInt(BUTTONS_COLOR, builder.buttonsColor);
        bundle.putInt(IMAGE, builder.image);
        bundle.putString(TITLE, builder.title);
        bundle.putString(DESCRIPTION, builder.description);
        bundle.putStringArray(NEEDED_PERMISSIONS, builder.neededPermissions);
        bundle.putStringArray(POSSIBLE_PERMISSIONS, builder.possiblePermissions);

        slideFragment.setArguments(bundle);
        return slideFragment;
    }

    public static boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        titleTextView = (TextView) view.findViewById(R.id.txt_title_slide);
        descriptionTextView = (TextView) view.findViewById(R.id.txt_description_slide);
        imageView = (ImageView) view.findViewById(R.id.image_slide);
        initializeView();
        return view;
    }

    public void initializeView() {
        Bundle bundle = getArguments();
        backgroundColor = bundle.getInt(BACKGROUND_COLOR);
        buttonsColor = bundle.getInt(BUTTONS_COLOR);
        image = bundle.getInt(IMAGE, 0);
        title = bundle.getString(TITLE);
        description = bundle.getString(DESCRIPTION);
        neededPermissions = bundle.getStringArray(NEEDED_PERMISSIONS);
        possiblePermissions = bundle.getStringArray(POSSIBLE_PERMISSIONS);

        updateViewWithValues();
    }

    public int backgroundColor() {
        return backgroundColor;
    }

    public int buttonsColor() {
        return buttonsColor;
    }


    public boolean canMoveFurther() {
        return true;
    }

    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.impassable_slide);
    }

    private void updateViewWithValues() {
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        if (image != 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), image));
            imageView.setVisibility(View.VISIBLE);
        }
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    private String[] removeEmptyAndNullStrings(final ArrayList<String> permissions) {
        List<String> list = new ArrayList<>(permissions);
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }
}

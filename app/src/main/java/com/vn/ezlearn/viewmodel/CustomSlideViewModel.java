package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class CustomSlideViewModel extends BaseObservable {
    private Context context;
    @Bindable
    public String title;
    @Bindable
    public String description;
    @Bindable
    public int image;
    @Bindable
    public int colorBg;

    public CustomSlideViewModel(Context context, String title, String description, int image,
                                int colorBg) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.image = image;
        this.colorBg = colorBg;
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("backgroundResource")
    public static void setBackgroudResource(ImageView imageView, int resource) {
        imageView.setBackgroundResource(resource);
    }
}

package com.vn.ezlearn.viewmodel

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.widget.ImageView

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

class CustomSlideViewModel(var title: String, var description: String, var image: Int,
                           var colorBg: Int) : BaseObservable() {
    var mTitle: ObservableField<String> = ObservableField(title)
    var mDescription: ObservableField<String> = ObservableField(description)
    var mImage: ObservableInt = ObservableInt(image)
    var mColorBg: ObservableInt = ObservableInt(colorBg)

    companion object {
        @JvmStatic
        @BindingAdapter("imageResource")
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }

        @JvmStatic
        @BindingAdapter("backgroundResource")
        fun setBackgroudResource(imageView: ImageView, resource: Int) {
            imageView.setBackgroundResource(resource)
        }
    }
}

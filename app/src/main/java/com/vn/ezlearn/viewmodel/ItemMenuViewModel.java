package com.vn.ezlearn.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.vn.ezlearn.models.Category;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class ItemMenuViewModel extends BaseObservable {
    @Bindable
    public Category category;

    public ItemMenuViewModel(Category category) {
        this.category = category;
    }
}

package com.vn.ezlearn.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.vn.ezlearn.model.ItemMenu;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class ItemMenuViewModel extends BaseObservable {
    @Bindable
    public ItemMenu itemMenu;

    public ItemMenuViewModel(ItemMenu itemMenu) {
        this.itemMenu = itemMenu;
    }
}

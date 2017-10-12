package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.vn.ezlearn.R;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 12/10/2017.
 */

public class ShowPointViewModel extends BaseObservable {
    public float point;
    public Context context;

    public ObservableField<String> tvPoint;

    public ShowPointViewModel(Context context, float point) {
        this.point = point;
        this.context = context;
        tvPoint = new ObservableField<>(point + " " + context.getString(R.string.point));
    }
}

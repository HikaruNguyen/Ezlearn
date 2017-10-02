package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.vn.ezlearn.R;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 02/10/2017.
 */

public class IntroViewModel extends BaseObservable {
    private Context context;
    public ObservableField<String> textNext;
    public ObservableField<String> textPreview;

    public IntroViewModel(Context context) {
        this.context = context;
        textNext = new ObservableField<>(context.getString(R.string.next));
        textPreview = new ObservableField<>(context.getString(R.string.skip));

    }
}

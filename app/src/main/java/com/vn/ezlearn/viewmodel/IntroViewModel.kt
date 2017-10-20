package com.vn.ezlearn.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField

import com.vn.ezlearn.R

/**
 * Created by FRAMGIA\nguyen.duc.manh on 02/10/2017.
 */

class IntroViewModel(context: Context) : BaseObservable() {
    var textNext: ObservableField<String> = ObservableField(context.getString(R.string.next))
    var textPreview: ObservableField<String> = ObservableField(context.getString(R.string.skip))

}

package com.vn.ezlearn.utils;

import android.view.View;

import com.vn.ezlearn.activity.MaterialIntroActivity;

import agency.tango.materialintroscreen.animations.ViewTranslationWrapper;


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class PermissionNotGrantedClickListener implements View.OnClickListener {
    private final MaterialIntroActivity activity;
    private final ViewTranslationWrapper translationWrapper;

    public PermissionNotGrantedClickListener(MaterialIntroActivity activity, ViewTranslationWrapper translationWrapper) {
        this.activity = activity;
        this.translationWrapper = translationWrapper;
    }

    @Override
    public void onClick(View v) {
        translationWrapper.error();
        activity.showPermissionsNotGrantedError();
    }
}
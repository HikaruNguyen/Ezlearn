package com.vn.ezlearn.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manhi on 8/6/2016.
 */

public class AppConfig {
    public static final String Pref = "Pref";

    public static final String KEY_IS_SHOW_INTRO = "key_is_shcow_intro";

    private SharedPreferences sharedPreferences;
    private Context context;
    private static AppConfig appConfig;

    public static AppConfig getInstance(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig(context);
        }
        return appConfig;
    }

    private AppConfig(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE);
    }

    public void setIsShowIntro(boolean isShow) {
        sharedPreferences.edit().putBoolean(KEY_IS_SHOW_INTRO, isShow).apply();
    }

    public boolean isShowIntro() {
        return sharedPreferences.getBoolean(KEY_IS_SHOW_INTRO, false);
    }
}

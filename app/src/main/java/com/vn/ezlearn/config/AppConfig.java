package com.vn.ezlearn.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manhi on 8/6/2016.
 */

public class AppConfig {
    private static final String Pref = "Pref";

    private static final String KEY_IS_SHOW_INTRO = "key_is_shcow_intro";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";

    private SharedPreferences sharedPreferences;
    private static AppConfig appConfig;

    public static AppConfig getInstance(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig(context);
        }
        return appConfig;
    }

    private AppConfig(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE);
    }

    public void setIsShowIntro(boolean isShow) {
        sharedPreferences.edit().putBoolean(KEY_IS_SHOW_INTRO, isShow).apply();
    }

    public boolean isShowIntro() {
        return sharedPreferences.getBoolean(KEY_IS_SHOW_INTRO, false);
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public void setName(String name) {
        sharedPreferences.edit().putString(KEY_NAME, name).apply();
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }
}

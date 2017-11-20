package com.vn.ezlearn.config

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by manhi on 8/6/2016.
 */

class AppConfig private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    var isShowIntro: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_SHOW_INTRO, false)
        set(isShow) = sharedPreferences.edit().putBoolean(KEY_IS_SHOW_INTRO, isShow).apply()

    init {
        this.sharedPreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE)
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private val Pref = "Pref"

        private val KEY_IS_SHOW_INTRO = "key_is_show_intro"
        private var appConfig: AppConfig? = null

        fun getInstance(context: Context): AppConfig {
            if (appConfig == null) {
                appConfig = AppConfig(context)
            }
            return appConfig as AppConfig
        }
    }
}

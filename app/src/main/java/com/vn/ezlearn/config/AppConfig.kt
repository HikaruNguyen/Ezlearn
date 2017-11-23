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

    var isSelectLevel: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_SELECT_LEVEL, false)
        set(isSelectLevel) = sharedPreferences.edit().putBoolean(KEY_IS_SELECT_LEVEL, isSelectLevel).apply()

    var studyLevelID: Int
        get() = sharedPreferences.getInt(KEY_ID_STUDY_LEVEL, 0)
        set(id) = sharedPreferences.edit().putInt(KEY_ID_STUDY_LEVEL, id).apply()

    var degreeID: Int
        get() = sharedPreferences.getInt(KEY_ID_DEGREE, 0)
        set(id) = sharedPreferences.edit().putInt(KEY_ID_DEGREE, id).apply()

    init {
        this.sharedPreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE)
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private val Pref = "Pref"

        private val KEY_IS_SHOW_INTRO = "key_is_show_intro"
        private val KEY_IS_SELECT_LEVEL = "key_is_select_level"
        private val KEY_ID_STUDY_LEVEL = "key_id_study_level"
        private val KEY_ID_DEGREE = "key_id_degree"
        private var appConfig: AppConfig? = null

        fun getInstance(context: Context): AppConfig {
            if (appConfig == null) {
                appConfig = AppConfig(context)
            }
            return appConfig as AppConfig
        }
    }
}

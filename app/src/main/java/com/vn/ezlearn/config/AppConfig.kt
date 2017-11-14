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

    var token: String
        get() = sharedPreferences.getString(KEY_TOKEN, "")
        set(token) = sharedPreferences.edit().putString(KEY_TOKEN, token).apply()

    var email: String
        get() = sharedPreferences.getString(KEY_EMAIL, "")
        set(email) = sharedPreferences.edit().putString(KEY_EMAIL, email).apply()

    var name: String
        get() = sharedPreferences.getString(KEY_NAME, "")
        set(name) = sharedPreferences.edit().putString(KEY_NAME, name).apply()
    var wallet: Long
        get() = sharedPreferences.getLong(KEY_WALLET, 0L)
        set(wallet) = sharedPreferences.edit().putLong(KEY_WALLET, wallet).apply()

    var user_package: String
        get() = sharedPreferences.getString(KEY_USER_PACKAGE, "")
        set(package_name) = sharedPreferences.edit().putString(KEY_USER_PACKAGE, package_name).apply()

    init {
        this.sharedPreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE)
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private val Pref = "Pref"

        private val KEY_IS_SHOW_INTRO = "key_is_shcow_intro"
        private val KEY_TOKEN = "key_token"
        private val KEY_NAME = "key_name"
        private val KEY_EMAIL = "key_email"
        private val KEY_WALLET = "key_wallet"
        private val KEY_USER_PACKAGE = "key_package"
        private var appConfig: AppConfig? = null

        fun getInstance(context: Context): AppConfig {
            if (appConfig == null) {
                appConfig = AppConfig(context)
            }
            return appConfig as AppConfig
        }
    }
}

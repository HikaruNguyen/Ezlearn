package com.vn.ezlearn.utils

import android.util.Log

import com.vn.ezlearn.BuildConfig

/**
 * Created by manhi on 26/12/2015.
 */
object CLog {
    var DEBUG = BuildConfig.DEBUG

    fun w(tag: String, msg: String): Int {
        return if (DEBUG) Log.w(tag, msg) else -1
    }

    fun d(tag: String, msg: String): Int {
        return if (DEBUG) Log.d(tag, msg) else -1
    }

    fun e(tag: String, msg: String): Int {
        return if (DEBUG) Log.e(tag, msg) else -1
    }

    fun v(tag: String, msg: String): Int {
        return if (DEBUG) Log.v(tag, msg) else -1
    }

    fun i(tag: String, msg: String): Int {
        return if (DEBUG) Log.i(tag, msg) else -1
    }
}

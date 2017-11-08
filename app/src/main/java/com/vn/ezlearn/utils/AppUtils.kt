package com.vn.ezlearn.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by FRAMGIA\nguyen.duc.manh on 12/10/2017.
 */

object AppUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun getTAG(cls: Class<*>): String {
        return cls.simpleName
    }
}

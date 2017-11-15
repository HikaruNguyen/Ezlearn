package com.vn.ezlearn.utils

import android.content.Context
import android.net.ConnectivityManager
import java.text.DecimalFormat


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

    fun formatMoney(number: Long): String {
        if (number < 1000) {
            return number.toString()
        }
        return try {
            val formatter = DecimalFormat("###,###")
            var resp = formatter.format(number.toLong())
            resp = resp.replace(",".toRegex(), ".")
            resp
        } catch (e: Exception) {
            ""
        }

    }
}

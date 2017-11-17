package com.vn.ezlearn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat


/**
 * Created by FRAMGIA\nguyen.duc.manh on 12/10/2017.
 */

object AppUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun getTAG(cls: Class<*>): String = cls.simpleName

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

    @SuppressLint("SimpleDateFormat")
    fun formatDateTime(time: String): String {
        val originalStringFormat = "yyyy-MM-dd HH:mm:ss"
        val desiredStringFormat = "dd/MM/yyyy"

        val readingFormat = SimpleDateFormat(originalStringFormat)
        val outputFormat = SimpleDateFormat(desiredStringFormat)

        try {
            val date = readingFormat.parse(time)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}

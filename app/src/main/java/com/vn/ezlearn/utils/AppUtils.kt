package com.vn.ezlearn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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

    @SuppressLint("SimpleDateFormat")
    fun formatLongToTime(timeLong: Long): String =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(timeLong))


    fun md5(s: String): String {
        val md5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                    .getInstance(md5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}

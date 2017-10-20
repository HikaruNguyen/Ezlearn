package com.vn.ezlearn.utils

import android.graphics.Color

/**
 * Created by FRAMGIA\nguyen.duc.manh on 02/10/2017.
 */

object ColorUtil {
    fun changeColorHSB(color: String): String {
        val hsv = FloatArray(3)
        val brandColor = Color.parseColor(color)
        Color.colorToHSV(brandColor, hsv)
        //        hsv[1] = hsv[1] + 0.1f;
        //        hsv[2] = hsv[2] - 0.1f;
        val argbColor = Color.HSVToColor(hsv)
        val hexColor = String.format("#%08X", argbColor)
        return hexColor
    }

}

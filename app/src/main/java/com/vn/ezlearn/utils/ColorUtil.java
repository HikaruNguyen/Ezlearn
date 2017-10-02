package com.vn.ezlearn.utils;

import android.graphics.Color;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 02/10/2017.
 */

public class ColorUtil {
    public static String changeColorHSB(String color) {
        float[] hsv = new float[3];
        int brandColor = Color.parseColor(color);
        Color.colorToHSV(brandColor, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int argbColor = Color.HSVToColor(hsv);
        String hexColor = String.format("#%08X", argbColor);
        return hexColor;
    }

}

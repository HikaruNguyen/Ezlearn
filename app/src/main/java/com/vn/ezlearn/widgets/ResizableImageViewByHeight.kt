package com.vn.ezlearn.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Created by Nguyen Duc Manh on 8/11/2015.
 */
class ResizableImageViewByHeight(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable

        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right edges
            //            int width = MeasureSpec.getSize(widthMeasureSpec);
            //            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            val height = View.MeasureSpec.getSize(heightMeasureSpec)
            val width = Math.ceil((height.toFloat() * d.intrinsicHeight.toFloat()
                    / d.intrinsicWidth.toFloat()).toDouble()).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
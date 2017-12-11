package com.vn.ezlearn.widgets

import `in`.srain.cube.views.ptr.*
import `in`.srain.cube.views.ptr.indicator.PtrIndicator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.pnikosis.materialishprogress.ProgressWheel
import com.vn.ezlearn.R

/**
 * Created by FRAMGIA\nguyen.duc.manh on 06/12/2017.
 */

class MyPullToRefresh : PtrClassicFrameLayout {
    private var onRefreshBegin: OnRefreshBegin? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setOnRefreshBegin(view: View, headerView: PullToRefreshHeader, onRefreshBegin: OnRefreshBegin) {
        this.onRefreshBegin = onRefreshBegin
        with(this) {
            setHeaderView(headerView)
            addPtrUIHandler(headerView)
            setPtrHandler(object : PtrHandler {
                override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                    return if (headerView.currentPosY < 1.5 * frame.headerHeight) {
                        PtrDefaultHandler.checkContentCanBePulledDown(frame, view, header)
                    } else false
                }

                override fun onRefreshBegin(frame: PtrFrameLayout) {
                    onRefreshBegin.refresh()
                }
            })
        }
    }


    interface OnRefreshBegin {
        fun refresh()
    }
    class PullToRefreshHeader : RelativeLayout, PtrUIHandler {

        private var progressWheel: ProgressWheel? = null
        private var layoutParams: RelativeLayout.LayoutParams? = null

        var currentPosY: Int = 0
            private set

        constructor(context: Context) : super(context) {
            initViews()
        }

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            initViews()
        }

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            initViews()
        }

        private fun initViews() {
            val header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header,
                    this)
            progressWheel = header.findViewById(R.id.progress_wheel)
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            setLayoutParams(layoutParams)
        }

        override fun onUIReset(frame: PtrFrameLayout) {
            progressWheel!!.visibility = View.VISIBLE
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            setLayoutParams(layoutParams)
        }

        override fun onUIRefreshPrepare(frame: PtrFrameLayout) {}

        override fun onUIRefreshBegin(frame: PtrFrameLayout) {
            progressWheel!!.spin()
        }

        override fun onUIRefreshComplete(frame: PtrFrameLayout) {
            progressWheel!!.visibility = View.GONE
        }

        override fun onUIPositionChange(frame: PtrFrameLayout, isUnderTouch: Boolean,
                                        status: Byte, ptrIndicator: PtrIndicator) {
            currentPosY = ptrIndicator.currentPosY
            val percent = Math.min(1f, ptrIndicator.currentPercent)
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                progressWheel!!.progress = percent

            }
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE || status == PtrFrameLayout.PTR_STATUS_LOADING) {
                if (ptrIndicator.currentPercent > 1f) {
                    layoutParams!!.bottomMargin = ptrIndicator.currentPosY - ptrIndicator.headerHeight
                    layoutParams!!.topMargin = ptrIndicator.headerHeight - ptrIndicator.currentPosY
                    setLayoutParams(layoutParams)
                }
            }
            invalidate()
        }
    }
}

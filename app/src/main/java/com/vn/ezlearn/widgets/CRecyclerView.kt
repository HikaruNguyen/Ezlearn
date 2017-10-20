package com.vn.ezlearn.widgets

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by manhi on 2/6/2016.
 */

open class CRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun init() {
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager
        this.setHasFixedSize(true)
        this.itemAnimator = DefaultItemAnimator()

    }

    fun setDivider() {
        this.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST))
    }
}

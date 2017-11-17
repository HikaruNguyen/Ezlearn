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
        this@CRecyclerView.layoutManager = layoutManager
        this@CRecyclerView.setHasFixedSize(true)
        this@CRecyclerView.itemAnimator = DefaultItemAnimator()

    }

    fun setDivider() {
        this@CRecyclerView.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST))
    }

    private fun isReadyForPullEnd(): Boolean {
        val lastView = this@CRecyclerView.getChildAt(this@CRecyclerView.childCount - 1)
        val lastPosition = this@CRecyclerView.getChildAdapterPosition(lastView)
        return if (lastPosition >= this@CRecyclerView.adapter.itemCount - 1) {
            this@CRecyclerView.getChildAt(this@CRecyclerView.childCount - 1).bottom <=
                    this@CRecyclerView.bottom
        } else false
    }

    fun loadMore(loadMoreListener: LoadMoreListener) {
        this@CRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (this@CRecyclerView.isReadyForPullEnd()) {
                    loadMoreListener.onScrolled()
                }

            }
        })
    }

    interface LoadMoreListener {
        fun onScrolled()
    }
}

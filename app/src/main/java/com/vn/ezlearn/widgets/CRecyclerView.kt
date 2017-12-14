package com.vn.ezlearn.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
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

    @SuppressLint("DuplicateDivider")
    class DividerItemDecoration(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {

        private val mDivider: Drawable

        private var mOrientation: Int = 0

        init {
            val a = context.obtainStyledAttributes(ATTRS)
            mDivider = a.getDrawable(0)
            a.recycle()
            setOrientation(orientation)
        }

        private fun setOrientation(orientation: Int) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw IllegalArgumentException("invalid orientation")
            }
            mOrientation = orientation
        }

        override fun onDraw(c: Canvas?, parent: RecyclerView?) {
            if (mOrientation == VERTICAL_LIST) {
                if (parent != null && c != null) {
                    drawVertical(c, parent)
                }
            } else {
                if (parent != null && c != null) {
                    drawHorizontal(c, parent)
                }
            }
        }

        private fun drawVertical(c: Canvas, parent: RecyclerView) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child
                        .layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }

        private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
            val top = parent.paddingTop
            val bottom = parent.height - parent.paddingBottom

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child
                        .layoutParams as RecyclerView.LayoutParams
                val left = child.right + params.rightMargin
                val right = left + mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }

        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?) =
                if (mOrientation == VERTICAL_LIST) {
                    outRect.set(0, 0, 0, mDivider.intrinsicHeight)
                } else {
                    outRect.set(0, 0, mDivider.intrinsicWidth, 0)
                }

        companion object {

            private val ATTRS = intArrayOf(android.R.attr.listDivider)

            val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL

            val VERTICAL_LIST = LinearLayoutManager.VERTICAL
        }
    }

}

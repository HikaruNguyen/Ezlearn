package com.vn.ezlearn.adapter

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.vn.ezlearn.BR
import com.vn.ezlearn.R
import com.vn.ezlearn.interfaces.OnClickQuestionPopupListener
import com.vn.ezlearn.models.MyContent
import com.vn.ezlearn.viewmodel.ItemQuestionDialogViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class DialogListQuestionAdapter : BaseRecyclerAdapter<MyContent, DialogListQuestionAdapter.ViewHolder> {
    private var onClickQuestionPopupListener: OnClickQuestionPopupListener? = null
    private var isShowPoint: Boolean = false

    constructor(context: Context, list: MutableList<MyContent>,
                onClickQuestionPopupListener: OnClickQuestionPopupListener) : super(context, list) {
        this.onClickQuestionPopupListener = onClickQuestionPopupListener
        isShowPoint = false
    }

    constructor(context: Context, list: MutableList<MyContent>,
                onClickQuestionPopupListener: OnClickQuestionPopupListener, isShowPoint: Boolean) :
            super(context, list) {
        this.onClickQuestionPopupListener = onClickQuestionPopupListener
        this.isShowPoint = isShowPoint
    }

    constructor(context: Context, list: MutableList<MyContent>) : super(context, list) {
        isShowPoint = true
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        viewDataBinding.setVariable(BR.itemQuestionDialogViewModel,
                ItemQuestionDialogViewModel(list[position], position + 1, isShowPoint))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                R.layout.item_dialog_question, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
            itemView.setOnClickListener {
                if (!isShowPoint) {
                    onClickQuestionPopupListener!!.onClick(adapterPosition)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("backgroundResource")
        fun setBackgroundResource(imageView: ImageView, resource: Int) {
            imageView.setBackgroundResource(resource)
        }
    }
}

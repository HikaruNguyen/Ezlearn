package com.vn.ezlearn.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vn.ezlearn.BR
import com.vn.ezlearn.R
import com.vn.ezlearn.models.HistoryBuyPackage
import com.vn.ezlearn.viewmodel.ItemHistoryBuyPackageViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class HistoryBuyPackageAdapter(context: Context, list: MutableList<HistoryBuyPackage>) :
        BaseRecyclerAdapter<HistoryBuyPackage, HistoryBuyPackageAdapter.ViewHolder>(context, list) {
    private var dialogChoose: AlertDialog? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        viewDataBinding.setVariable(BR.itemHistoryBuyPackageViewModel,
                ItemHistoryBuyPackageViewModel(mContext, list[position]))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                R.layout.item_history_buy_package, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
            itemView.setOnClickListener {

            }
        }
    }
}
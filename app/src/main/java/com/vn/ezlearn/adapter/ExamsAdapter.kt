package com.vn.ezlearn.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vn.ezlearn.BR
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.databinding.ItemDocumentBinding
import com.vn.ezlearn.databinding.ItemHomeExamsBinding
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.viewmodel.ItemDocumentViewModel
import com.vn.ezlearn.viewmodel.ItemExamViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ExamsAdapter(context: Context, list: MutableList<ContentByCategory>) :
        BaseRecyclerAdapter<ContentByCategory, ExamsAdapter.ViewHolder>(context, list) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        when (getItemViewType(position)) {
            ContentByCategory.CONTENT_TYPE_EXAM -> {
                viewDataBinding.setVariable(BR.itemExamViewModel,
                        ItemExamViewModel(mContext, list[position].exam))
                viewDataBinding.root.setOnClickListener {
                    if (!AppConfig.getInstance(mContext).token.isEmpty()) {
                        val intent = Intent(mContext, TestActivity::class.java)
                        intent.putExtra(TestActivity.KEY_ID, list[position].exam?.id)
                        intent.putExtra(TestActivity.KEY_NAME, list[position].exam?.subject_code)
                        mContext.startActivity(intent)
                    } else {
                        showDialogLogin()
                    }
                }
            }
            ContentByCategory.CONTENT_TYPE_DOCUMENT -> {
                viewDataBinding.setVariable(BR.itemDocumentViewModel,
                        ItemDocumentViewModel(mContext, list[position].document))
                viewDataBinding.root.setOnClickListener {
                    if (!AppConfig.getInstance(mContext).token.isEmpty()) {

                    } else {
                        showDialogLogin()
                    }
                }
            }
        }

    }

    private fun showDialogLogin(){
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage(mContext.getString(R.string.needLogin))
        builder.setPositiveButton(R.string.ok)
        { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            ContentByCategory.CONTENT_TYPE_EXAM -> {
                val binding = DataBindingUtil.inflate<ItemHomeExamsBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_home_exams, parent, false)

                return ViewHolder(binding)
            }
            ContentByCategory.CONTENT_TYPE_DOCUMENT -> {
                val binding = DataBindingUtil.inflate<ItemDocumentBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_document, parent, false)

                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemHomeExamsBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_home_exams, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].contentType!!
    }

    inner class ViewHolder(val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

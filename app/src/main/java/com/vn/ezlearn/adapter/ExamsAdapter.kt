package com.vn.ezlearn.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.BR
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ItemDocumentBinding
import com.vn.ezlearn.databinding.ItemHomeExamsBinding
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.viewmodel.ItemDocumentViewModel
import com.vn.ezlearn.viewmodel.ItemExamViewModel
import rx.Subscription

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ExamsAdapter(context: Context, list: MutableList<ContentByCategory>) :
        BaseRecyclerAdapter<ContentByCategory, ExamsAdapter.ViewHolder>(context, list) {
    private var mSubscription: Subscription? = null
    private var apiService: EzlearnService? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ContentByCategory.CONTENT_TYPE_EXAM -> {
                val viewDataBinding = holder.itemExamBinding
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
                val viewDataBinding = holder.itemDocumentBinding
                val itemExamViewModel = ItemDocumentViewModel(mContext, list[position].document, list[position].document?.isDownloaded)
                viewDataBinding.itemDocumentViewModel = itemExamViewModel
                if (list[position].document!!.isDownloaded) {
                    viewDataBinding.root.setOnClickListener {
                        if (!AppConfig.getInstance(mContext).token.isEmpty()) {

                        } else {
                            showDialogLogin()
                        }
                    }
                } else {
                    viewDataBinding.download.setOnClickListener {
                        itemExamViewModel.visiableProgress.set(View.VISIBLE)
                        dowloadFile(list[position].document!!.file_url)
                    }
                }

            }
        }

    }

    private fun dowloadFile(file_url: String?) {
        apiService = MyApplication.with(mContext).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()

    }

    private fun showDialogLogin() {
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

    inner class ViewHolder : RecyclerView.ViewHolder {
        lateinit var itemExamBinding: ItemHomeExamsBinding
        lateinit var itemDocumentBinding: ItemDocumentBinding

        constructor(itemExamBinding: ItemHomeExamsBinding) : super(itemExamBinding.root) {
            this.itemExamBinding = itemExamBinding
            this.itemExamBinding.executePendingBindings()
        }

        constructor(itemDocumentBinding: ItemDocumentBinding) : super(itemDocumentBinding.root) {
            this.itemDocumentBinding = itemDocumentBinding
            this.itemDocumentBinding.executePendingBindings()
        }
    }
}

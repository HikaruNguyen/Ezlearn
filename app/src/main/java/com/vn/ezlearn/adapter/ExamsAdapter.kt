package com.vn.ezlearn.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.databinding.ItemHomeExamsBinding
import com.vn.ezlearn.models.Exam
import com.vn.ezlearn.viewmodel.ItemExamViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ExamsAdapter(context: Context, list: MutableList<Exam>) :
        BaseRecyclerAdapter<Exam, ExamsAdapter.ViewHolder>(context, list) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        viewDataBinding.itemExamViewModel = ItemExamViewModel(mContext, list[position])
        viewDataBinding.lnExam.setOnClickListener {
            if (!AppConfig.getInstance(mContext).token.isEmpty()) {
                val intent = Intent(mContext, TestActivity::class.java)
                intent.putExtra(TestActivity.KEY_ID, list[position].id)
                intent.putExtra(TestActivity.KEY_NAME, list[position].subject_code)
                mContext.startActivity(intent)
            } else {
                val builder = AlertDialog.Builder(mContext)
                builder.setMessage(mContext.getString(R.string.needLogin))
                builder.setPositiveButton(R.string.ok)
                { dialogInterface, _ -> dialogInterface.dismiss() }
                builder.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemHomeExamsBinding>(
                LayoutInflater.from(parent.context), R.layout.item_home_exams, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ItemHomeExamsBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

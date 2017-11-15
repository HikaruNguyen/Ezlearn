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
import com.vn.ezlearn.databinding.DialogHistoryExamChooseBinding
import com.vn.ezlearn.models.HistoryExam
import com.vn.ezlearn.viewmodel.ItemHistoryExamViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class HistoryExamAdapter(context: Context, list: MutableList<HistoryExam>) :
        BaseRecyclerAdapter<HistoryExam, HistoryExamAdapter.ViewHolder>(context, list) {
    private var dialogChoose: AlertDialog? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        viewDataBinding.setVariable(BR.itemHistoryExamViewModel,
                ItemHistoryExamViewModel(mContext, list[position]))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                R.layout.item_history_exam, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
            itemView.setOnClickListener {
                showPopup(adapterPosition)

            }
        }

        private fun showPopup(adapterPosition: Int) {
            if (dialogChoose != null && dialogChoose!!.isShowing) {
                dialogChoose!!.dismiss()
            }
            val builder = AlertDialog.Builder(mContext)
            val dialogHistoryExam = DataBindingUtil.inflate<DialogHistoryExamChooseBinding>(
                    LayoutInflater.from(mContext), R.layout.dialog_history_exam_choose, null, false)
            builder.setView(dialogHistoryExam.root)

            dialogHistoryExam.tvReview.setOnClickListener {
                dialogChoose!!.dismiss()
                val intent = Intent(mContext, TestActivity::class.java)
                intent.putExtra(TestActivity.KEY_ID, list[adapterPosition].subject_id)
                intent.putExtra(TestActivity.KEY_NAME, list[adapterPosition].subject_code)
                intent.putExtra(TestActivity.KEY_IS_REVIEW, true)
                intent.putExtra(TestActivity.KEY_ANSWER, list[adapterPosition].answers)
                mContext.startActivity(intent)
            }
            dialogHistoryExam.tvRetesting.setOnClickListener {
                dialogChoose!!.dismiss()
                val intent = Intent(mContext, TestActivity::class.java)
                intent.putExtra(TestActivity.KEY_ID, list[adapterPosition].subject_id)
                intent.putExtra(TestActivity.KEY_NAME, list[adapterPosition].subject_code)
                intent.putExtra(TestActivity.KEY_IS_REVIEW, false)
                mContext.startActivity(intent)
            }
            builder.setCancelable(true)
            dialogChoose = builder.create()
            dialogChoose!!.show()
        }
    }
}
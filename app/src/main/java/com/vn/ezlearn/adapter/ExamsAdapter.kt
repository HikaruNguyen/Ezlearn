package com.vn.ezlearn.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.vn.ezlearn.BR
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.ItemDocumentBinding
import com.vn.ezlearn.databinding.ItemHomeExamsBinding
import com.vn.ezlearn.interfaces.OnClickDownloadListener
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.viewmodel.ItemDocumentViewModel
import com.vn.ezlearn.viewmodel.ItemExamViewModel
import java.io.File


/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

class ExamsAdapter(context: Context, list: MutableList<ContentByCategory>) :
        BaseRecyclerAdapter<ContentByCategory, ExamsAdapter.ViewHolder>(context, list) {
    lateinit var onClickDownloadListener: OnClickDownloadListener

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ContentByCategory.CONTENT_TYPE_EXAM -> {
                val viewDataBinding = holder.itemExamBinding
                viewDataBinding.setVariable(BR.itemExamViewModel,
                        ItemExamViewModel(mContext, list[position].exam))
                viewDataBinding.root.setOnClickListener {
                    if (!UserConfig.getInstance(mContext).token.isEmpty()) {
                        val intent = Intent(mContext, TestActivity::class.java)
                        with(intent) {
                            putExtra(TestActivity.KEY_ID, list[position].exam?.id)
                            putExtra(TestActivity.KEY_NAME, list[position].exam?.subject_code)
                            putExtra(TestActivity.KEY_TIME, list[position].exam?.time)
                        }
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
                        if (!UserConfig.getInstance(mContext).token.isEmpty()) {
                            if (list[position].document!!.isDownloaded) {
                                var filePath = Environment.getExternalStorageDirectory().toString() + "/ezlearn"
                                filePath += "/" + list[position].document?.name_en?.replace(" ", "") + ".doc"
                                openDocument(filePath)
                            }
                        } else {
                            showDialogLogin()
                        }
                    }
                } else {
                    viewDataBinding.download.setOnClickListener {
                        if (list[position].document != null && list[position].document!!.file_url != null) {
                            downloadFile(list[position].document!!.name_en, list[position].document!!.file_url!!, position)
                        }

                    }
                }

            }
        }

    }

    private fun openDocument(path: String) {
        val intent = Intent(android.content.Intent.ACTION_VIEW)
        val file = File(path)
        val extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        if (extension.equals("", ignoreCase = true) || mimeType == null) {
            // if there is no extension or there is no definite mimeType, still try to open the file
            intent.setDataAndType(Uri.fromFile(file), "text/*")
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimeType)
        }
        // custom message for the intent
        mContext.startActivity(Intent.createChooser(intent, "Choose an Application:"))
    }

    private fun downloadFile(name: String, file_url: String, position: Int) =
            if (AppUtils.isNetworkAvailable(mContext)) {
                onClickDownloadListener.onClick(name, BuildConfig.ENDPOINT_DOWNLOAD + file_url, position)
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.error_connect), Toast.LENGTH_SHORT).show()
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

    override fun getItemViewType(position: Int): Int = list[position].contentType!!

    inner class ViewHolder : RecyclerView.ViewHolder {
        lateinit var itemExamBinding: ItemHomeExamsBinding
        lateinit var itemDocumentBinding: ItemDocumentBinding

        constructor(itemExamBinding: ItemHomeExamsBinding) : super(itemExamBinding.root) {
            this.itemExamBinding = itemExamBinding.apply { executePendingBindings() }
        }

        constructor(itemDocumentBinding: ItemDocumentBinding) : super(itemDocumentBinding.root) {
            this.itemDocumentBinding = itemDocumentBinding.apply { executePendingBindings() }
        }
    }


}

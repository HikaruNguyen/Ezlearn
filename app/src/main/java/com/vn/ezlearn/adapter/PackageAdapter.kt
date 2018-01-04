package com.vn.ezlearn.adapter

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vn.ezlearn.BR
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ItemPackageBinding
import com.vn.ezlearn.models.Package
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.viewmodel.ItemPackageViewModel
import rx.Subscription

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class PackageAdapter(context: Context, list: MutableList<Package>) :
        BaseRecyclerAdapter<Package, PackageAdapter.ViewHolder>(context, list) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewDataBinding = holder.viewDataBinding
        viewDataBinding.setVariable(BR.itemPackageViewModel,
                ItemPackageViewModel(mContext, list[position]))
        list[position].file_image?.let {
            Picasso.with(mContext).load(BuildConfig.ENDPOINT_DOWNLOAD + it).into(viewDataBinding.imgIcon)
        }
        viewDataBinding.btnRegisterPackage.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            with(builder) {
                setTitle(mContext.getString(R.string.confirm))
                setMessage(if (list[position].is_myPackage) {
                    mContext.getString(R.string.confirm_cancel_package,
                            list[position].package_display_name)
                } else {
                    mContext.getString(R.string.confirm_register_package,
                            list[position].package_display_name,
                            AppUtils.formatMoney(list[position].price!!))
                })
                setPositiveButton(if (list[position].is_myPackage) {
                    mContext.getString(R.string.cancel_package)
                } else {
                    mContext.getString(R.string.register_package)
                }) { dialog, _ ->
                    if (list[position].is_myPackage) {
                        cancelPackage(position)
                    } else {
                        registerPackage(position)
                    }
                    dialog.dismiss()
                }

                setNegativeButton(mContext.getString(R.string.cancel), { dialog, _ ->
                    dialog.dismiss()
                })
                setCancelable(false)
                show()
            }
        }

    }

    private fun registerPackage(position: Int) {
        Log.d(AppUtils.getTAG(PackageAdapter::class.java), "position: " + position)
    }

    private fun cancelPackage(position: Int) {
        Log.d(AppUtils.getTAG(PackageAdapter::class.java), "position: " + position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPackageBinding>(LayoutInflater.from(parent.context),
                R.layout.item_package, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val viewDataBinding: ItemPackageBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
            itemView.setOnClickListener {

            }
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("backgroundResource")
        fun setBackgroundResource(imageView: ImageView, resource: Int) {
            imageView.setBackgroundResource(resource)
        }

        @JvmStatic
        @BindingAdapter("textColor")
        fun setTextColor(textView: TextView, color: Int) {
            textView.setTextColor(color)
        }
    }
}
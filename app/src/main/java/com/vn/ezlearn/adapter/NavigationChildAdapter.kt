package com.vn.ezlearn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.vn.ezlearn.R
import com.vn.ezlearn.config.GlobalValue
import com.vn.ezlearn.interfaces.NavigationItemChildSelected
import com.vn.ezlearn.models.Category
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.utils.CLog

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

class NavigationChildAdapter(context: Context, list: MutableList<Category>,
                             private var navigationItemSelected: NavigationItemChildSelected,
                             private var parentPosition: Int) :
        BaseRecyclerAdapter<Category, NavigationChildAdapter.ViewHolder>(context, list) {
    private val expandState = SparseBooleanArray()

    init {
        for (i in list.indices) {
            expandState.append(i, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_menu_child, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            setIsRecyclable(false)
//        if (item.isSelected) {
//            holder.item!!.setBackgroundResource(R.drawable.bg_item_navigation_press)
//        } else {
//            holder.item!!.setBackgroundResource(R.drawable.bg_item_navigation)
//        }
            tvName.text = item.category_name
            this@with.item!!.setOnClickListener {
                //            setBackgroundPress(position)
                navigationItemSelected.onSelected(list[position].category_name,
                        list[position].category_id,
                        list[position].children, parentPosition)


            }
        }

    }

    override fun getItemViewType(position: Int): Int = list[position].type!!

    override fun getItemCount(): Int = list.size

    private fun setBackgroundPress(position: Int) {
        CLog.d(AppUtils.getTAG(NavigationChildAdapter::class.java), "position_menu_parent_old: "
                + GlobalValue.position_menu_parent_old + " " + parentPosition)
        clearBackground(false)
//        if (position != GlobalValue.position_menu_child_old) {
        val category: Category?
        category = list[position]
        category.isSelected = true
        setData(position, category)
        GlobalValue.position_menu_child_old = position
        notifyDataSetChanged()

//        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView = v.findViewById(R.id.tvName)
        var item: RelativeLayout? = v.findViewById(R.id.item)
    }

    fun clearBackground(isRefresh: Boolean) {
        var category: Category?
        for (i in list.indices) {
            if (list[i].isSelected) {
                category = list[i]
                category.isSelected = false
                setData(i, category)
            }
        }
        if (isRefresh) {
            notifyDataSetChanged()
        }

    }

}
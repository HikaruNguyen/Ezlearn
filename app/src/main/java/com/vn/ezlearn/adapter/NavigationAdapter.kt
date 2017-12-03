package com.vn.ezlearn.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.aakira.expandablelayout.ExpandableLayout
import com.github.aakira.expandablelayout.ExpandableLinearLayout
import com.github.aakira.expandablelayout.Utils
import com.vn.ezlearn.R
import com.vn.ezlearn.config.GlobalValue
import com.vn.ezlearn.interfaces.NavigationItemChildSelected
import com.vn.ezlearn.interfaces.NavigationItemSelected
import com.vn.ezlearn.models.Category
import com.vn.ezlearn.widgets.CRecyclerView

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

class NavigationAdapter(context: Context, list: MutableList<Category>,
                        private val navigationItemSelected: NavigationItemSelected) :
        BaseRecyclerAdapter<Category, NavigationAdapter.ViewHolder>(context, list),
        NavigationItemChildSelected {

    override fun onSelected(name: String, id: String, categoryList: List<Category>?,
                            parentPosition: Int) {
//        CLog.d(AppUtils.getTAG(NavigationAdapter::class.java), "position_menu_parent_old: "
//                + GlobalValue.position_menu_parent_old + " " + parentPosition)
//        if (GlobalValue.position_menu_parent_old != parentPosition) {
//            if (GlobalValue.position_menu_parent_old > 0)
//                list[GlobalValue.position_menu_parent_old].childAdapter!!.clearBackground(true)
//            GlobalValue.position_menu_parent_old = parentPosition
//        }
//        GlobalValue.position_menu_old = parentPosition
//        clearBackground()
        navigationItemSelected.onSelected(name, id, categoryList)
    }

    private val data: List<Category>
    private var context: Context? = null
    private val expandState = SparseBooleanArray()

    init {
        this.data = list
        for (i in data.indices) {
            expandState.append(i, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        return when (viewType) {
            Category.TYPE_NORMAL -> ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_menu_normal, parent, false))
            Category.TYPE_LINE -> ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_menu_line, parent, false))
            else -> ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_menu, parent, false))
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (getItemViewType(position) != Category.TYPE_LINE) {
            val item = data[position]
            holder.setIsRecyclable(false)
            holder.tvName?.text = item.category_name

            if (getItemViewType(position) != Category.TYPE_NORMAL) {
                val childAdapter = NavigationChildAdapter(
                        mContext, ArrayList(), this, position)
                item.childAdapter = childAdapter
                holder.rvItemMenuChild?.adapter = childAdapter
                childAdapter.addAll(item.children!!)
                holder.expandableLayout?.setInRecyclerView(true)
                holder.expandableLayout?.setInterpolator(Utils.createInterpolator(
                        Utils.FAST_OUT_SLOW_IN_INTERPOLATOR))
                holder.expandableLayout?.isExpanded = expandState.get(position)
//                holder.expandableLayout?.setListener(object : ExpandableLayoutListenerAdapter() {
//                    override fun onPreOpen() {
//                        createRotateAnimator(holder.buttonLayout!!, 0f, 180f).start()
//                        expandState.put(position, true)
//                    }
//
//                    override fun onPreClose() {
//                        createRotateAnimator(holder.buttonLayout!!, 180f, 0f).start()
//                        expandState.put(position, false)
//                    }
//                })

                holder.buttonLayout?.rotation = if (expandState.get(position)) 180f else 0f
//                holder.buttonLayout?.setOnClickListener { onClickButton(holder.expandableLayout!!) }
//                holder.item?.setOnClickListener { onClickButton(holder.expandableLayout!!) }
            } else {
//                if (item.isSelected) {
//                    holder.item!!.setBackgroundResource(R.drawable.bg_item_navigation_press)
//                } else {
//                    holder.item!!.setBackgroundResource(R.drawable.bg_item_navigation)
//                }
                if (item.levelChild!! >= 2) {
                    holder.itemView.setOnClickListener {
//                        setBackgroundPress(position)
                        navigationItemSelected.onSelected(item.category_name, item.category_id,
                                item.children!!)
                    }

                } else {
                    holder.itemView.setOnClickListener {
//                        setBackgroundPress(position)
                        navigationItemSelected.onSelected(item.category_name, item.category_id, null)
                    }
                }

            }

        }

    }

    private fun setBackgroundPress(position: Int) {
        if (position != GlobalValue.position_menu_old) {
            var category = list[GlobalValue.position_menu_old]
            category.isSelected = false
            setData(GlobalValue.position_menu_old, category)
            category = list[position]
            category.isSelected = true
            setData(position, category)
            GlobalValue.position_menu_old = position
            list.indices
                    .filter { list[it].childAdapter != null && list[it].childAdapter!!.itemCount > 0 }
                    .forEach { list[it].childAdapter!!.clearBackground(true) }
            notifyDataSetChanged()
        }
    }


    private fun onClickButton(expandableLayout: ExpandableLayout) {
        expandableLayout.toggle()
    }

    override fun getItemViewType(position: Int): Int = data[position].typeMenu!!

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView? = v.findViewById(R.id.tvName)
        var buttonLayout: RelativeLayout? = v.findViewById(R.id.button)
        var rvItemMenuChild: CRecyclerView? = v.findViewById(R.id.rvItemMenuChild)
        var expandableLayout: ExpandableLinearLayout? = v.findViewById(R.id.expandableLayout)
        var item: RelativeLayout? = v.findViewById(R.id.item)

    }

    fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)
        return animator
    }

    private fun clearBackground() {
        var category: Category?
        for (i in list.indices) {
            if (list[i].isSelected) {
                category = list[i]
                category.isSelected = false
                setData(i, category)
            }
        }
        notifyDataSetChanged()

    }
}
package com.vn.ezlearn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vn.ezlearn.R
import com.vn.ezlearn.interfaces.NavigationItemSelected
import com.vn.ezlearn.models.Category

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

class NavigationChildAdapter(context: Context, list: MutableList<Category>,
                             var navigationItemSelected: NavigationItemSelected) :
        BaseRecyclerAdapter<Category, NavigationChildAdapter.ViewHolder>(context, list) {

    private val data: List<Category>
    private var context: Context? = null
    private val expandState = SparseBooleanArray()

    init {
        this.data = list
        for (i in data.indices) {
            expandState.append(i, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_menu_child, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.setIsRecyclable(false)
        holder.tvName.text = item.category_name
    }

    override fun getItemViewType(position: Int): Int = data[position].type!!

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView = v.findViewById(R.id.tvName)

        init {
            itemView.setOnClickListener {
                navigationItemSelected.onSelected(list[adapterPosition].category_name,
                        list[adapterPosition].category_id,
                        list[adapterPosition].children)
            }
        }
    }

}
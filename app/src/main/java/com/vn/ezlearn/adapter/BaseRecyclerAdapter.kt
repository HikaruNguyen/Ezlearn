package com.vn.ezlearn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*

/**
 * Created by manhi on 20/1/2016.
 */
abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(
        var mContext: Context, list: MutableList<T>) : RecyclerView.Adapter<VH>() {
    var list: MutableList<T> = ArrayList()
    private val layoutInflater: LayoutInflater

    init {
        this.list = list
        this.layoutInflater = LayoutInflater.from(mContext)
    }

    override fun getItemCount(): Int = list.size

    fun setData(position: Int, item: T) {
        list[position] = item
        this.notifyItemChanged(position)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        list.removeAt(position)
        this.notifyItemRemoved(position)
    }

    fun add(pos: Int, item: T) {
        list.add(pos, item)
        notifyItemInserted(pos)
    }

    fun add(item: T) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }


    fun addAll(listItems: List<T>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }

    fun getItemByPosition(position: Int): T = list[position]

    abstract override fun onBindViewHolder(holder: VH, position: Int)

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

}
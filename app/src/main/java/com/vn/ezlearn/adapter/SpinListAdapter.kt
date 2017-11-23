package com.vn.ezlearn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.vn.ezlearn.R

/**
 * Created by FRAMGIA\nguyen.duc.manh on 23/11/2017.
 */

class SpinListAdapter(context: Context, resource: Int, private val listString: List<String>) :
        ArrayAdapter<String>(context, resource, listString) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_select_level, parent, false)
        val amount = view.findViewById<TextView>(R.id.textItem)
        amount.text = listString[position]
        return view
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("ViewHolder") val view = inflater.inflate(R.layout.item_select_level, parent, false)
        val amount = view.findViewById<TextView>(R.id.textItem)
        amount.text = listString[position]
        return view
    }

    override fun getCount(): Int = listString.size

}

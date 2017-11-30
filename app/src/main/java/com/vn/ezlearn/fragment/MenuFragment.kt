package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.NavigationAdapter
import com.vn.ezlearn.databinding.FragmentMenuBinding
import com.vn.ezlearn.interfaces.NavigationItemSelected
import com.vn.ezlearn.models.Category


/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment(), NavigationItemSelected {
    override fun onSelected(name: String, id: String, categoryList: List<Category>?) {

    }

    private lateinit var menuBinding: FragmentMenuBinding
    private lateinit var menuList: ArrayList<Category>
    private lateinit var navigationAdapter: NavigationAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        menuBinding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_menu, container, false)
        bindData()
        return menuBinding.root
    }

    private fun bindData() {
        menuList = ArrayList()
        if (MyApplication.with(context).categoryResult != null
                && MyApplication.with(context).categoryResult!!.data != null
                && MyApplication.with(context).categoryResult!!.data!!.isNotEmpty()) {
            menuList.addAll(MyApplication.with(context).categoryResult!!.data!!)
            navigationAdapter = NavigationAdapter(context, menuList, this)
            menuBinding.rvNavigation.adapter = navigationAdapter
        }
    }

}

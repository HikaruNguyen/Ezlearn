package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.FragmentCategoryMainBinding
import com.vn.ezlearn.fragment.CategoryFragment
import com.vn.ezlearn.models.Category

class CategoryMainActivity : BaseActivity() {
    private lateinit var categoryMainBinding: FragmentCategoryMainBinding

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var fragmentList: MutableList<Fragment>
    private lateinit var fragmentListTitle: MutableList<String>

    private var categoryList: List<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryMainBinding = DataBindingUtil.setContentView(this, R.layout.fragment_category_main)
        setSupportActionBar(categoryMainBinding.toolbar!!)
        setBackButtonToolbar()
        getIntentData()
        bindData()
    }

    private fun getIntentData() {
        val name = intent.getStringExtra(KEY_NAME)
        categoryMainBinding.toolbar.title = name
        val jsonList = intent.getStringExtra(KEY_LIST)
        categoryList = ArrayList()
        val gson = Gson()
        categoryList = gson.fromJson(jsonList, object :
                TypeToken<List<Category>>() {}.type)
    }

    private fun bindData() {

        fragmentList = ArrayList()
        fragmentListTitle = ArrayList()
        if (categoryList != null && categoryList!!.isNotEmpty()) {
            for (i in categoryList!!.indices) {
                fragmentList.add(CategoryFragment.newInstance(
                        Integer.parseInt(categoryList!![i].category_id),
                        Integer.parseInt(categoryList!![i].content_type)))
                fragmentListTitle.add(categoryList!![i].category_name)
            }
        }
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList,
                fragmentListTitle)
        categoryMainBinding.viewPager.adapter = viewPagerAdapter
        categoryMainBinding.tabs.setupWithViewPager(
                categoryMainBinding.viewPager)
    }

    companion object {
        val KEY_LIST = "KEY_LIST"
        val KEY_NAME = "KEY_NAME"
    }
}

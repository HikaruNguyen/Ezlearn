package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MainActivity
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.FragmentCategoryMainBinding
import com.vn.ezlearn.models.Category
import java.util.*

class CategoryMainFragment : Fragment() {
    private var categoryID: String? = null

    private var categoryMainBinding: FragmentCategoryMainBinding? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var fragmentList: MutableList<Fragment>? = null
    private var fragmentListTitle: MutableList<String>? = null

    private var categoryList: List<Category>? = null

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                categoryMainBinding!!.viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                categoryMainBinding!!.viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                categoryMainBinding!!.viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_document -> {
                categoryMainBinding!!.viewPager.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            categoryID = arguments.getString(CATEGORY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        categoryMainBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_category_main, container, false)
        categoryMainBinding!!.navigation.setOnNavigationItemSelectedListener(
                mOnNavigationItemSelectedListener)
        bindData()
        event()
        return categoryMainBinding!!.root
    }

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList
    }

    private fun event() {
        categoryMainBinding!!.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    categoryMainBinding!!.navigation.selectedItemId = R.id.navigation_home
                } else if (position == 1) {
                    categoryMainBinding!!.navigation.selectedItemId = R.id.navigation_dashboard
                } else if (position == 2) {
                    categoryMainBinding!!.navigation.selectedItemId = R.id.navigation_notifications
                } else if (position == 3) {
                    categoryMainBinding!!.navigation.selectedItemId = R.id.navigation_document
                } else {
                    categoryMainBinding!!.navigation.selectedItemId = R.id.navigation_home
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun bindData() {
        fragmentList = ArrayList()
        fragmentListTitle = ArrayList()
        if (categoryList != null && categoryList!!.isNotEmpty()) {
            for (i in categoryList!!.indices) {
                fragmentList!!.add(CategoryFragment.newInstance(
                        Integer.parseInt(categoryList!![i].category_id),
                        Integer.parseInt(categoryList!![i].content_type)))
                fragmentListTitle!!.add(categoryList!![i].category_name)
            }
        }
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, fragmentList!!,
                fragmentListTitle!!)
        categoryMainBinding!!.viewPager.adapter = viewPagerAdapter
        (activity as MainActivity).tabLayout.setupWithViewPager(
                categoryMainBinding!!.viewPager)
    }

    companion object {


        private val CATEGORY_ID = "CATEGORY_ID"

        fun newInstance(categoryID: String): CategoryMainFragment {
            val fragment = CategoryMainFragment()
            val args = Bundle()
            args.putString(CATEGORY_ID, categoryID)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

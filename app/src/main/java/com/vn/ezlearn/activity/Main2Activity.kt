package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityMain2Binding
import com.vn.ezlearn.fragment.HomeFragment
import com.vn.ezlearn.fragment.MenuFragment
import java.util.*

class Main2Activity : AppCompatActivity() {
    private var fragmentList: MutableList<Fragment>? = null
    private lateinit var main2Binding: ActivityMain2Binding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home ->

                return@OnNavigationItemSelectedListener true
            R.id.navigation_dashboard ->

                return@OnNavigationItemSelectedListener true
            R.id.navigation_notifications ->

                return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentList = ArrayList()
        fragmentList!!.add(HomeFragment())
        fragmentList!!.add(MenuFragment())
        fragmentList!!.add(HomeFragment())
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
        main2Binding.viewPager.adapter = viewPagerAdapter

    }

}

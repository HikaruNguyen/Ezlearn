package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityMain2Binding
import com.vn.ezlearn.fragment.AccountFragment
import com.vn.ezlearn.fragment.HomeFragment
import com.vn.ezlearn.fragment.MenuFragment
import java.util.*

class Main2Activity : AppCompatActivity() {
    private var fragmentList: MutableList<Fragment>? = null
    private lateinit var main2Binding: ActivityMain2Binding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                main2Binding.viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                main2Binding.viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                main2Binding.viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
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
        fragmentList!!.add(AccountFragment())
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
        main2Binding.viewPager.adapter = viewPagerAdapter
        main2Binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> main2Binding.navigation.selectedItemId =
                            R.id.navigation_home
                    1 -> main2Binding.navigation.selectedItemId =
                            R.id.navigation_dashboard
                    2 -> main2Binding.navigation.selectedItemId =
                            R.id.navigation_notifications
                    else -> main2Binding.navigation.selectedItemId =
                            R.id.navigation_home
                }
            }

        })

    }

}

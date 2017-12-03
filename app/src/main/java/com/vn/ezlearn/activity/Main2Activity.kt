package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityMain2Binding
import com.vn.ezlearn.fragment.AccountFragment
import com.vn.ezlearn.fragment.HomeFragment
import com.vn.ezlearn.fragment.MenuFragment
import com.vn.ezlearn.utils.BottomNavigationViewHelper
import java.util.*

class Main2Activity : AppCompatActivity() {
    private var fragmentList: MutableList<Fragment>? = null
    private lateinit var main2Binding: ActivityMain2Binding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                main2Binding.viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_category -> {
                main2Binding.viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_free -> {
//                main2Binding.viewPager.currentItem = 2
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.nav_account -> {
                main2Binding.viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        supportActionBar!!.title = getString(R.string.nav_home)
        BottomNavigationViewHelper.disableShiftMode(main2Binding.navigation)
        main2Binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentList = ArrayList()
        fragmentList!!.add(HomeFragment())
        fragmentList!!.add(MenuFragment())
//        fragmentList!!.add(CategoryFragment.newInstance(AppConstant.FREE_ID,
//                ContentByCategory.CONTENT_TYPE_EXAM))
        fragmentList!!.add(AccountFragment())
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
        main2Binding.viewPager.adapter = viewPagerAdapter
        main2Binding.viewPager.offscreenPageLimit = fragmentList!!.size
        main2Binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =
                    Unit

            override fun onPageSelected(position: Int) = when (position) {
                0 -> {
                    main2Binding.navigation.selectedItemId =
                            R.id.nav_home
                    supportActionBar!!.title = getString(R.string.nav_home)
                }
                1 -> {
                    main2Binding.navigation.selectedItemId =
                            R.id.nav_category
                    supportActionBar!!.title = getString(R.string.nav_category)
                }
//                2 -> {
//                    main2Binding.navigation.selectedItemId =
//                            R.id.nav_free
//                    supportActionBar!!.title = getString(R.string.nav_free_exam)
//                }
                2 -> {
                    main2Binding.navigation.selectedItemId =
                            R.id.nav_account
                    supportActionBar!!.title = getString(R.string.nav_account)
                }
                else -> {
                    main2Binding.navigation.selectedItemId =
                            R.id.nav_home
                    supportActionBar!!.title = getString(R.string.nav_home)
                }
            }
        })

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (main2Binding.viewPager.currentItem != 0) {
            main2Binding.viewPager.setCurrentItem(0)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.click_back_again), Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

        }
    }
}

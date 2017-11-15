package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityUserProfileBinding
import com.vn.ezlearn.fragment.HistoryExamFragment
import com.vn.ezlearn.fragment.HistoryTransactionFragment
import com.vn.ezlearn.fragment.UserProfileFragment
import com.vn.ezlearn.interfaces.UserInfoCallBack
import com.vn.ezlearn.models.User
import com.vn.ezlearn.utils.BottomNavigationViewHelper
import com.vn.ezlearn.viewmodel.UserMainViewModel
import java.util.*


class UserProfile : BaseActivity(), UserInfoCallBack {

    private var userProfileBinding: ActivityUserProfileBinding? = null
    private var fragmentList: MutableList<Fragment>? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var userMainViewModel: UserMainViewModel

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                userProfileBinding!!.viewPager.currentItem = 0
                userMainViewModel.visiableAvatar.set(View.VISIBLE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history_exam -> {
                userProfileBinding!!.viewPager.currentItem = 1
                userMainViewModel.visiableAvatar.set(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history_topup -> {
                userProfileBinding!!.viewPager.currentItem = 2
                userMainViewModel.visiableAvatar.set(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_history_buy_package -> {
//                userProfileBinding!!.viewPager.currentItem = 3
//                userMainViewModel.visiableAvatar.set(View.GONE)
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        userMainViewModel = UserMainViewModel(this@UserProfile)
        userProfileBinding!!.userMainViewModel = userMainViewModel
        userProfileBinding!!.navigation.setOnNavigationItemSelectedListener(
                mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(userProfileBinding!!.navigation)
        setSupportActionBar(userProfileBinding!!.toolbar)
        setBackButtonToolbar()
        userProfileBinding!!.toolbar.title = ""
        bindData()
        event()

    }

    private fun event() {
//        userProfileBinding!!.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
//            val percentage = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
//            when {
//                percentage > 0.75 -> userProfileBinding!!.icAvatar.alpha = 0f
//                percentage > 0.5 -> userProfileBinding!!.icAvatar.alpha = 1 - percentage
//                else -> userProfileBinding!!.icAvatar.alpha = 1f
//            }
//        }

        userProfileBinding!!.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> userProfileBinding!!.navigation.selectedItemId =
                            R.id.navigation_profile
                    1 -> userProfileBinding!!.navigation.selectedItemId =
                            R.id.navigation_history_exam
                    2 -> userProfileBinding!!.navigation.selectedItemId =
                            R.id.navigation_history_topup
//                    3 -> userProfileBinding!!.navigation.selectedItemId =
//                            R.id.navigation_history_buy_package
                    else -> userProfileBinding!!.navigation.selectedItemId =
                            R.id.navigation_profile
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun bindData() {
        fragmentList = ArrayList()
        val userProfileFragment = UserProfileFragment()
        userProfileFragment.setUserInfoCallBack(this@UserProfile)
        fragmentList!!.add(userProfileFragment)
        fragmentList!!.add(HistoryExamFragment())
        fragmentList!!.add(HistoryTransactionFragment())
//        fragmentList!!.add(UserProfileFragment())
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
        userProfileBinding!!.viewPager.adapter = viewPagerAdapter

    }

    override fun onLoadUserInfoSuccess(user: User?) {
        if (user != null) {
            userMainViewModel.setUserInfo(user)
        }
    }
}

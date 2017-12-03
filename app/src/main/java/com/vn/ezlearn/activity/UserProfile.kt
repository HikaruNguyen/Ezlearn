package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityUserProfileBinding
import com.vn.ezlearn.fragment.UserProfileFragment
import com.vn.ezlearn.interfaces.UserInfoCallBack
import com.vn.ezlearn.models.User
import com.vn.ezlearn.viewmodel.UserMainViewModel
import java.util.*


class UserProfile : BaseActivity(), UserInfoCallBack {

    private lateinit var userProfileBinding: ActivityUserProfileBinding
    private var fragmentList: MutableList<Fragment>? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var userMainViewModel: UserMainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        userMainViewModel = UserMainViewModel(this@UserProfile)
        userProfileBinding.userMainViewModel = userMainViewModel
        setSupportActionBar(userProfileBinding.toolbar)
        setBackButtonToolbar()
        userProfileBinding.toolbar.title = ""
        bindData()

    }

    private fun bindData() {
        fragmentList = ArrayList()
        val userProfileFragment = UserProfileFragment()
        userProfileFragment.setUserInfoCallBack(this@UserProfile)
        fragmentList!!.add(userProfileFragment)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
        userProfileBinding.viewPager.adapter = viewPagerAdapter

    }

    override fun onLoadUserInfoSuccess(user: User?) {
        if (user != null) {
            userMainViewModel.setUserInfo(user)
        }
    }
}

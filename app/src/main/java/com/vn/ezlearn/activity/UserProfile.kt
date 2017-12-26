package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.UserPackageAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityUserProfileBinding
import com.vn.ezlearn.interfaces.UserInfoCallBack
import com.vn.ezlearn.modelresult.UserInfoResult
import com.vn.ezlearn.models.User
import com.vn.ezlearn.viewmodel.UserMainViewModel
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserProfile : BaseActivity(), UserInfoCallBack {

    private lateinit var userProfileBinding: ActivityUserProfileBinding
    //    private var fragmentList: MutableList<Fragment>? = null
//    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var userMainViewModel: UserMainViewModel
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private lateinit var mUserInfoResult: UserInfoResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        userMainViewModel = UserMainViewModel(this@UserProfile)
        userProfileBinding.userMainViewModel = userMainViewModel
        setSupportActionBar(userProfileBinding.toolbar)
        setBackButtonToolbar()
        userProfileBinding.toolbar.title = ""
        apiService = MyApplication.with(this).getEzlearnService()
        bindData()

    }

    private fun bindData() {
//        fragmentList = ArrayList()
//        val userProfileFragment = UserProfileFragment()
//        userProfileFragment.setUserInfoCallBack(this@UserProfile)
//        fragmentList!!.add(userProfileFragment)
//        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList!!)
//        userProfileBinding.viewPager.adapter = viewPagerAdapter
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.getUserInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<UserInfoResult>() {
                    override fun onCompleted() {
                        if (mUserInfoResult.success!!) {
                            mUserInfoResult.data?.let {
                                if (it.user_packages != null) {
                                    val userPackageAdapter = UserPackageAdapter(this@UserProfile, ArrayList())
                                    userProfileBinding.rvUserPackage.adapter = userPackageAdapter
                                    userPackageAdapter.addAll(it.user_packages!!)
                                }
                                if (it.user != null) {
                                    userMainViewModel.setUserInfo(it.user!!)
                                }
                            }

                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(userInfoResult: UserInfoResult?) {
                        if (userInfoResult != null) {
                            mUserInfoResult = userInfoResult
                        }
                    }
                })
    }

    override fun onLoadUserInfoSuccess(user: User?) {
        if (user != null) {
            userMainViewModel.setUserInfo(user)
        }
    }
}

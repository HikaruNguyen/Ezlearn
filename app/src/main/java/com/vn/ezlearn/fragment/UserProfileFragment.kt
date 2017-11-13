package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.UserPackageAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentUserProfileBinding
import com.vn.ezlearn.modelresult.UserInfoResult
import com.vn.ezlearn.viewmodel.UserInfoViewModel
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {

    private var userProfileBinding: FragmentUserProfileBinding? = null
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private lateinit var mUserInfoResult: UserInfoResult
    private lateinit var userInfoViewModel: UserInfoViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        userProfileBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_user_profile, container, false)
        loadUserInfo()
        return userProfileBinding!!.root
    }

    private fun loadUserInfo() {
        apiService = MyApplication.with(activity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.getUserInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<UserInfoResult>() {
                    override fun onCompleted() {
                        if (mUserInfoResult.success!! && mUserInfoResult.data != null) {
                            userInfoViewModel = UserInfoViewModel(activity, mUserInfoResult.data)
                            userProfileBinding!!.userInfoViewModel = userInfoViewModel
                            if (mUserInfoResult.data?.user_packages != null) {
                                val userPackageAdapter = UserPackageAdapter(activity, ArrayList())
                                userProfileBinding!!.rvUserPackage.adapter = userPackageAdapter
                                userPackageAdapter.addAll(mUserInfoResult.data!!.user_packages!!)
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

}

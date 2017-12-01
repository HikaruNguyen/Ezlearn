package com.vn.ezlearn.fragment


import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vn.ezlearn.R
import com.vn.ezlearn.activity.LoginActivity
import com.vn.ezlearn.activity.UserProfile
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.FragmentAccountBinding
import com.vn.ezlearn.viewmodel.AccountViewModel


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {
    private lateinit var accountBinding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        accountBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_account, container, false)
        accountViewModel = AccountViewModel(activity)
        accountBinding.accountViewModel = accountViewModel
        bindData()
        event()
        return accountBinding.root
    }

    private fun bindData() {

    }

    private fun event() {
        accountBinding.rlLogin.setOnClickListener {
            val intent: Intent
            if (!UserConfig.getInstance(activity).token.isEmpty()) {
                intent = Intent(activity, UserProfile::class.java)
                startActivity(intent)
            } else {
                intent = Intent(activity, LoginActivity::class.java)
                startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LoginActivity.LOGIN_REQUEST) {
                accountViewModel.updateProfile(UserConfig.getInstance(activity).name)
            }
        }
    }

}

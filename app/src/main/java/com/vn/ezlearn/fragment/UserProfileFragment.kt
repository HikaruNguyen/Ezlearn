package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vn.ezlearn.R
import com.vn.ezlearn.databinding.FragmentUserProfileBinding

/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {

    private var userProfileBinding: FragmentUserProfileBinding? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        userProfileBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_user_profile, container, false)
        return userProfileBinding!!.root
    }

}// Required empty public constructor

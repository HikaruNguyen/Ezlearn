package com.vn.ezlearn.interfaces

import com.vn.ezlearn.models.User

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

interface UserInfoCallBack {
    fun onLoadUserInfoSuccess(user: User?)
}

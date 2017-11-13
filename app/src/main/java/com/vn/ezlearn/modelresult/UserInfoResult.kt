package com.vn.ezlearn.modelresult

import com.vn.ezlearn.models.User
import com.vn.ezlearn.models.UserPackage

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/11/2017.
 */

class UserInfoResult {
    var success: Boolean? = false
    var data: UserInfoData? = null

    class UserInfoData {
        var user: User? = null
        var user_packages: MutableList<UserPackage>? = null
    }
}

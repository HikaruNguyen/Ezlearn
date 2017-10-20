package com.vn.ezlearn.modelresult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class LoginResult {
    var success: Boolean = false
    var data: LoginData? = null

    class LoginData {
        var message: String = ""
        var id: Int? = null
        var access_token: String? = null
        var avatar: String? = null
        var display_name: String? = null
    }
}

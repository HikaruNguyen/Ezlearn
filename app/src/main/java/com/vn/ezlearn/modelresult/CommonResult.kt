package com.vn.ezlearn.modelresult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

class CommonResult {
    var success: Boolean = false
    var data: BaseDateResult? = null

    class BaseDateResult {
        var message: String = ""
    }
}

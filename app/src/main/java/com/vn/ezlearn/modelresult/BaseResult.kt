package com.vn.ezlearn.modelresult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 06/12/2017.
 */

class BaseResult<T> {
    var success: Boolean = false
    var data: MutableList<T>? = null
}

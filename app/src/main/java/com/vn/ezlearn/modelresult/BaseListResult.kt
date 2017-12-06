package com.vn.ezlearn.modelresult

/**
 * Created by FRAMGIA\nguyen.duc.manh on 06/12/2017.
 */

class BaseListResult<T> {
    var success: Boolean? = null
    var data: BaseData<T>? = null

    class BaseData<T> {
        var list: MutableList<T>? = null
        var totalCount: Int? = null
    }
}

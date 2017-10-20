package com.vn.ezlearn.modelresult

import com.vn.ezlearn.models.Category

import java.io.Serializable

/**
 * Created by FRAMGIA\nguyen.duc.manh on 25/09/2017.
 */

class CategoryResult : Serializable {
    var success: Boolean = false
    var data: List<Category>? = null
}

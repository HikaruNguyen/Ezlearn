package com.vn.ezlearn.models

import java.util.*

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

class Category {

    var category_id: String = ""
    var category_name: String = ""
    var type: Int? = null
    var parent_id: String = ""
    var weight: String? = null
    var img_icon: String? = null
    var children: List<Category>? = null
    var typeMenu: Int? = null
    var levelChild: Int? = null
    var content_type: String = "0"

    constructor(id: String, name: String, type: Int?) {
        this.category_id = id
        this.category_name = name
        this.typeMenu = type
        this.parent_id = "-1"
        children = ArrayList<Category>()
        levelChild = LEVEL_1
    }

    constructor(type: Int?) {
        this.typeMenu = type
        levelChild = LEVEL_0
    }

    companion object {
        val TYPE_NORMAL = 1
        val TYPE_PARENT = 2
        val TYPE_CHILD = 3
        val TYPE_LINE = 4

        val LEVEL_3 = 3
        val LEVEL_2 = 2
        val LEVEL_1 = 1
        val LEVEL_0 = 0
    }
}

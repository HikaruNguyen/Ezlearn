package com.vn.ezlearn.interfaces

import com.vn.ezlearn.models.Category

/**
 * Created by admin on 9/16/17.
 */

interface NavigationItemSelected {
    fun onSelected(name: String, id: String, categoryList: List<Category>?)
}

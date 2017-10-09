package com.vn.ezlearn.interfaces;

import com.vn.ezlearn.models.Category;

import java.util.List;

/**
 * Created by admin on 9/16/17.
 */

public interface NavigationItemSelected {
    void onSelected(String name, String id, List<Category> categoryList);
}

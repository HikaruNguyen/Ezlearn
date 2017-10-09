package com.vn.ezlearn.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class Category {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_PARENT = 2;
    public static final int TYPE_CHILD = 3;
    public static final int TYPE_LINE = 4;

    public String category_id;
    public String category_name;
    public Integer type;
    public String parent_id;
    public String weight;
    public String img_icon;
    public List<CategoryChild> children;
    public Integer typeMenu;

    public Category(String id, String name, int type, String parent_id) {
        this.category_id = id;
        this.category_name = name;
        this.typeMenu = type;
        this.parent_id = parent_id;
        children = new ArrayList<>();
    }

    //
//    public Category(int id, String name, int type, int parentId, List<CategoryChild> menuChildList) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.parentId = parentId;
//        this.menuChildList = menuChildList;
//    }
//
    public Category(String id, String name, Integer type) {
        this.category_id = id;
        this.category_name = name;
        this.typeMenu = type;
        this.parent_id = "-1";
        children = new ArrayList<>();
    }

    public Category(Integer type) {
        this.typeMenu = type;
    }
}

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

    public static final int LEVEL_3 = 3;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_0 = 0;

    public String category_id;
    public String category_name;
    public Integer type;
    public String parent_id;
    public String weight;
    public String img_icon;
    public List<Category> children;
    public Integer typeMenu;
    public Integer levelChild;

    public Category(String id, String name, Integer type) {
        this.category_id = id;
        this.category_name = name;
        this.typeMenu = type;
        this.parent_id = "-1";
        children = new ArrayList<>();
        levelChild = LEVEL_1;
    }

    public Category(Integer type) {
        this.typeMenu = type;
        levelChild = LEVEL_0;
    }
}

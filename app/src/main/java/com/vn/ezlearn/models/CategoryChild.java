package com.vn.ezlearn.models;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class CategoryChild {
    public static final int TYPE_PARENT = 2;
    public static final int TYPE_CHILD = 3;
    public String category_id;
    public String category_name;
    public Integer type;
    public String parent_id;
    public String weight;
    public String img_icon;

    public CategoryChild(String id, String name, int type, String parentId) {
        this.category_id = id;
        this.category_name = name;
        this.type = type;
        this.parent_id = parentId;
    }
}

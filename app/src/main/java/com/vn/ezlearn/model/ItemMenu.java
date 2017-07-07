package com.vn.ezlearn.model;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class ItemMenu {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_PARENT = 2;
    public static final int TYPE_CHILD = 3;
    public int id;
    public String name;
    public String type;
    public int parentId;

    public ItemMenu(int id, String name, String type, int parentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
    }
}

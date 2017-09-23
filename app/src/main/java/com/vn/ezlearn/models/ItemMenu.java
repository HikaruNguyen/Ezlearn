package com.vn.ezlearn.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 07/07/2017.
 */

public class ItemMenu {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_PARENT = 2;
    public static final int TYPE_CHILD = 3;
    public int id;
    public String name;
    public int type;
    public int parentId;
    public List<ItemMenuChild> menuChildList;

    public ItemMenu(int id, String name, int type, int parentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        menuChildList = new ArrayList<>();
    }

    public ItemMenu(int id, String name, int type, int parentId, List<ItemMenuChild> menuChildList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.menuChildList = menuChildList;
    }

    public ItemMenu(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = -1;
        menuChildList = new ArrayList<>();
    }
}

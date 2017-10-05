package com.vn.ezlearn.models;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class Banner {
    public static final int TYPE_URL = 1;
    public static final int TYPE_DRAWABLE = 2;
    public Integer id;
    public String imageUrl;
    public int imageDrawable;
    public int type;

    public Banner(Integer id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        type = TYPE_URL;
    }
    public Banner(String imageUrl) {
        this.imageUrl = imageUrl;
        type = TYPE_URL;
    }
    public Banner(Integer id, int imageDrawable) {
        this.id = id;
        this.imageDrawable = imageDrawable;
        type = TYPE_DRAWABLE;
    }
}

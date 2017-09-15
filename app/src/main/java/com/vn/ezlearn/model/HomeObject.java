package com.vn.ezlearn.model;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

public class HomeObject {
    public static final int TYPE_SLIDE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_EXAM = 3;

    public Category category;
    public Exam exam;
    public List<Banner> bannerList;
    public int type;

    public HomeObject(Category category) {
        this.category = category;
        type = TYPE_HEADER;
    }

    public HomeObject(Exam exam) {
        this.exam = exam;
        type = TYPE_EXAM;
    }

    public HomeObject(List<Banner> bannerList) {
        this.bannerList = bannerList;
        type = TYPE_SLIDE;
    }
}

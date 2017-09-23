package com.vn.ezlearn.models;

import java.util.List;

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

public class QuestionObject {
    public static final int TYPE_PART = 2;
    public static final int TYPE_VIEWPAGER = 3;

    public String part;
    public List<Question> list;
    public int type;

    public QuestionObject(String part) {
        this.part = part;
        this.type = TYPE_PART;
    }

    public QuestionObject(List<Question> list) {
        this.list = list;
        this.type = TYPE_VIEWPAGER;
    }
}

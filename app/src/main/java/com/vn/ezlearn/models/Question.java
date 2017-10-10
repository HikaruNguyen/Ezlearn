package com.vn.ezlearn.models;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class Question {
    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_READING = 2;
    public Region region;
    public Integer type;
    public List<Content> question;
    public List<Reading> reading;
}

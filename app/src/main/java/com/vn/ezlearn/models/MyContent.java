package com.vn.ezlearn.models;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class MyContent {
    public static final int TYPE_ANSWERED = 1;
    public static final int TYPE_NO_ANSWER = 2;
    public static final int TYPE_LATE = 3;

    public int typeQuestion;

    public Region region;
    public Integer type;
    public Content content;
    public String passage;

    public Float point;
    public Boolean isCorrect;

    public MyContent(Region region, Integer type, Content content, Float point) {
        this.region = region;
        this.type = type;
        this.content = content;
        this.point = point;
        this.isCorrect = false;
        this.typeQuestion = TYPE_NO_ANSWER;
    }

    public MyContent(Region region, Integer type, Content content, String passage, Float point) {
        this.region = region;
        this.type = type;
        this.content = content;
        this.passage = passage;
        this.point = point;
        this.isCorrect = false;
        this.typeQuestion = TYPE_NO_ANSWER;
    }
}

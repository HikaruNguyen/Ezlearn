package com.vn.ezlearn.models;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

public class Question {
    public static final int TYPE_ANSWERED = 1;
    public static final int TYPE_UNANSWER = 2;
    public static final int TYPE_LATE = 3;
    public Integer id;
    public int type;

    public String part;
    public String passage;
    public String question;

//    public Question(Integer id, int type) {
//        this.id = id;
//        this.type = type;
//    }

    public Question(Integer id, int type, String part, String passage, String question) {
        this.id = id;
        this.type = type;
        this.part = part;
        this.passage = passage;
        this.question = question;
    }
}

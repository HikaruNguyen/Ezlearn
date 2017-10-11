package com.vn.ezlearn.models;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class Answer {
    public static final Integer IS_TRUE = 1;

    public Integer id;
    public Integer question_id;
    public String answer;
    public Integer is_true;
    public String mark;
    public String created_at;
    public String updated_at;
    public Integer status;
    public String weight;
}

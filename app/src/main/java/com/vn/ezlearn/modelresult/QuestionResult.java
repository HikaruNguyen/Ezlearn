package com.vn.ezlearn.modelresult;

import com.vn.ezlearn.models.Content;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class QuestionResult {
    public Boolean success;
    public QuestionData data;

    public static class QuestionData {
        public List<Content> data;
    }
}

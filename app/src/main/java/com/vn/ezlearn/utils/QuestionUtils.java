package com.vn.ezlearn.utils;

import com.vn.ezlearn.models.MyContent;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/10/2017.
 */

public class QuestionUtils {
    private static QuestionUtils questionUtils;
    private List<MyContent> myContentList;

    public static QuestionUtils getInstance() {
        if (questionUtils == null) {
            questionUtils = new QuestionUtils();
        }
        return questionUtils;
    }

    public void clearMyContentList() {
        myContentList = null;
    }

    public List<MyContent> getMyContentList() {
        return myContentList;
    }

    public void setMyContentList(List<MyContent> myContentList) {
        this.myContentList = myContentList;
    }
}

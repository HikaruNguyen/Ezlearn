package com.vn.ezlearn.modelresult;

import com.vn.ezlearn.models.Category;
import com.vn.ezlearn.models.Exam;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

public class ExamsResult {
    public Boolean success;
    public ExamsData data;

    public static class ExamsData {
        public Category category;
        public List<Exam> list;
        public Integer totalCount;
    }
}

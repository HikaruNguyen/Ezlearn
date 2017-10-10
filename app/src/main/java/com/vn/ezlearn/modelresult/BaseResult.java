package com.vn.ezlearn.modelresult;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class BaseResult {
    public Boolean success;
    public BaseDateResult data;

    public static class BaseDateResult {
        public String message;
    }
}

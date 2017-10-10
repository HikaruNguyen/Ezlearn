package com.vn.ezlearn.modelresult;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 10/10/2017.
 */

public class LoginResult {
    public Boolean success;
    public LoginData data;

    public static class LoginData {
        public String message;
        public Integer id;
        public String access_token;
        public String avatar;
        public String display_name;
    }
}

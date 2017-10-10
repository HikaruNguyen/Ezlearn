package com.vn.ezlearn.models;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

public class Content {

    public Integer id;
    public String content;
    public String category_id;
    public String file_image;
    public String file_audio;
    public String status;
    public String type;
    public String parent_id;
    public String created_at;
    public String updated_at;
    public String weight;
    public String sub_question;
    public String suggest;
    public String answer_show;
    public List<Answer> answer_list;
//    public String part;
//    public String passage;
//    public String question;


//    public Content(Integer id, int type, String part, String passage, String question) {
//        this.id = id;
//        this.typeQuestion = type;
//        this.part = part;
//        this.passage = passage;
//        this.question = question;
//    }
}

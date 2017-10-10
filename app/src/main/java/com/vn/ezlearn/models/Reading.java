package com.vn.ezlearn.models;

import java.util.List;

/**
 * Created by Windows 10 Version 2 on 10/10/2017.
 */

public class Reading {
    public Integer id;
    public String content;
    public String category_id;
    public String file_image;
    public String status;
    public String type;
    public String parent_id;
    public String created_at;
    public String updated_at;
    public String weight;
    public String file_audio;
    public String is_listening;
    public List<Content> questions;
}

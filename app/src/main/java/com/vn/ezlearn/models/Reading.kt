package com.vn.ezlearn.models

/**
 * Created by Windows 10 Version 2 on 10/10/2017.
 */

class Reading {
    var id: Int? = null
    var content: String = ""
    var category_id: String? = null
    var file_image: String? = null
    var status: String? = null
    var type: String? = null
    var parent_id: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var weight: String? = null
    var file_audio: String? = null
    var is_listening: String? = null
    var questions: List<Content>? = null
}

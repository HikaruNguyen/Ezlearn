package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 21/09/2017.
 */

class Content {

    var id: Int? = null
    var content: String = ""
    var category_id: String? = null
    var file_image: String? = null
    var file_audio: String? = null
    var status: String? = null
    var type: String? = null
    var parent_id: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var weight: String? = null
    var sub_question: String? = null
    var suggest: String? = null
    var answer_show: String? = null
    var answer_list: List<Answer>? = null
}

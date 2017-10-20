package com.vn.ezlearn.models

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

class Banner {
    var id: Int? = null
    var imageUrl: String = ""
    var imageDrawable: Int = 0
    var type: Int = 0

    constructor(id: Int?, imageUrl: String) {
        this.id = id
        this.imageUrl = imageUrl
        type = TYPE_URL
    }

    constructor(imageUrl: String) {
        this.imageUrl = imageUrl
        type = TYPE_URL
    }

    constructor(id: Int?, imageDrawable: Int) {
        this.id = id
        this.imageDrawable = imageDrawable
        type = TYPE_DRAWABLE
    }

    companion object {
        val TYPE_URL = 1
        val TYPE_DRAWABLE = 2
    }
}

package com.vn.ezlearn.interfaces

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

interface OnCheckAnswerListener {
    fun onCheckAnswer(position: Int, answer: Int)
    fun onInputAnswer(position: Int, answer: String)

    fun onNeedReview(position: Int)
}

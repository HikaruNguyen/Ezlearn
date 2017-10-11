package com.vn.ezlearn.interfaces;

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

public interface OnCheckAnswerListener {
    void OnCheckAnswer(int position, int answer);

    void onNeedReview(int position);
}

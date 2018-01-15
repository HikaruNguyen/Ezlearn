package com.vn.ezlearn.eventbus;

import com.vn.ezlearn.models.Answer;

/**
 * Created by admin on 14/1/2018.
 */

public class AnswerEvent {
    private Answer answer;

    public AnswerEvent(Answer answer) {
        this.answer = answer;
    }
}

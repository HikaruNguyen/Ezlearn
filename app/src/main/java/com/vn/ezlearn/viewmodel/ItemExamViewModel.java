package com.vn.ezlearn.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.vn.ezlearn.R;
import com.vn.ezlearn.models.Exam;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 05/10/2017.
 */

public class ItemExamViewModel extends BaseObservable {
    private Context context;
    private Exam exam;
    public ObservableField<String> name;
    public ObservableField<String> time;
    public ObservableField<String> date_start_end;
    public ObservableField<String> total_question;
    public ObservableField<String> total_view;

    public ObservableInt visiableFree;

    public ItemExamViewModel(Context context, Exam exam) {
        this.context = context;
        this.exam = exam;
        name = new ObservableField<>();
        time = new ObservableField<>();
        date_start_end = new ObservableField<>();
        total_question = new ObservableField<>();
        total_view = new ObservableField<>();
        visiableFree = new ObservableInt(View.GONE);
        bindExam();
    }

    private void bindExam() {
        if (exam != null) {
            if (exam.subject_code != null) {
                name.set(exam.subject_code);
            }
            if (exam.time != null) {
                time.set(exam.time + " " + context.getString(R.string.minute));
            }
            if (exam.start_date != null && exam.end_date != null) {
                date_start_end.set(exam.start_date + " " + exam.end_date);
            }
            if (exam.is_free != null && exam.is_free == 1) {
                visiableFree.set(View.VISIBLE);
            } else {
                visiableFree.set(View.GONE);
            }
            if (exam.total_view != null) {
                total_view.set(exam.total_view + " " + context.getString(R.string.view));
            } else {
                total_view.set("N/A");
            }
        }
    }
}

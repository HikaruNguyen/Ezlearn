package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.HistoryExamAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentHistoryExamBinding
import com.vn.ezlearn.modelresult.HistoryExamResult
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * A simple [Fragment] subclass.
 */
class HistoryExamFragment : Fragment() {
    private lateinit var historyExamBinding: FragmentHistoryExamBinding
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private lateinit var mHistoryExamResult: HistoryExamResult
    private lateinit var adapter: HistoryExamAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        historyExamBinding = DataBindingUtil.inflate(inflater!!,
                R.layout.fragment_history_exam, container, false)
        bindData()
        return historyExamBinding.root
    }

    private fun bindData() {
        adapter = HistoryExamAdapter(activity, ArrayList())
        historyExamBinding.rvHistoryHome.adapter = adapter
        historyExamBinding.rvHistoryHome.setDivider()
        getListHistoryExam(1)
    }

    private fun getListHistoryExam(page: Int) {
        apiService = MyApplication.with(activity).getEzlearnService()
        mSubscription = apiService!!.getHistoryExam(page, 5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<HistoryExamResult>() {
                    override fun onCompleted() {
                        if (mHistoryExamResult.success && mHistoryExamResult.data != null
                                && mHistoryExamResult.data!!.list != null
                                && mHistoryExamResult.data!!.list!!.isNotEmpty()) {
                            adapter.addAll(mHistoryExamResult.data!!.list!!)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(historyExamResult: HistoryExamResult?) {
                        if (historyExamResult != null) {
                            mHistoryExamResult = historyExamResult
                        }
                    }
                })
    }

}

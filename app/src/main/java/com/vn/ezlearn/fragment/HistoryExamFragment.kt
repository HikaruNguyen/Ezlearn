package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.HistoryBuyPackageAdapter
import com.vn.ezlearn.adapter.HistoryExamAdapter
import com.vn.ezlearn.adapter.HistoryPaymentAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentHistoryExamBinding
import com.vn.ezlearn.modelresult.BaseListResult
import com.vn.ezlearn.models.HistoryBuyPackage
import com.vn.ezlearn.models.HistoryExam
import com.vn.ezlearn.models.HistoryPayment
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
    private lateinit var adapterHistoryExam: HistoryExamAdapter
    private lateinit var adapterHistoryBuyPackage: HistoryBuyPackageAdapter
    private lateinit var adapterHistoryPayment: HistoryPaymentAdapter
    private var historyType: Int? = TYPE_EXAM
    private var current_page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            historyType = arguments.getInt(HistoryExamFragment.TYPE_HISTORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        historyExamBinding = DataBindingUtil.inflate(inflater!!,
                R.layout.fragment_history_exam, container, false)
        bindData()
        return historyExamBinding.root
    }

    private fun bindData() {
        historyExamBinding.rvHistoryHome.setDivider()
        when (historyType) {
            TYPE_EXAM -> {
                adapterHistoryExam = HistoryExamAdapter(activity, ArrayList())
                historyExamBinding.rvHistoryHome.adapter = adapterHistoryExam
                getListHistoryExam(current_page)
            }
            TYPE_PAYMENT -> {
                adapterHistoryPayment = HistoryPaymentAdapter(activity, ArrayList())
                historyExamBinding.rvHistoryHome.adapter = adapterHistoryPayment
                getListHistoryPayment(current_page)
            }
            TYPE_BUY_PACKAGE -> {
                adapterHistoryBuyPackage = HistoryBuyPackageAdapter(activity, ArrayList())
                historyExamBinding.rvHistoryHome.adapter = adapterHistoryBuyPackage
                getListHistoryBuyPackage(current_page)
            }
            else -> {
                getListHistoryExam(current_page)
            }
        }
//        historyExamBinding.rvHistoryHome.setDivider()
    }

    private fun getListHistoryExam(page: Int) {
        var mBaseListResult: BaseListResult<HistoryExam>? = null
        apiService = MyApplication.with(activity).getEzlearnService()
        mSubscription = apiService!!.getHistoryExam(page, 5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseListResult<HistoryExam>>() {
                    override fun onCompleted() {
                        if (mBaseListResult!!.success && mBaseListResult!!.data != null
                                && mBaseListResult?.data!!.list != null
                                && mBaseListResult?.data!!.list!!.isNotEmpty()) {
                            adapterHistoryExam.addAll(mBaseListResult?.data!!.list!!)
                        }
                    }

                    override fun onError(e: Throwable) = Unit

                    override fun onNext(historyResult: BaseListResult<HistoryExam>?) {
                        if (historyResult != null) {
                            mBaseListResult = historyResult
                        }
                    }
                })
    }

    private fun getListHistoryBuyPackage(page: Int) {
        var mBaseListResult: BaseListResult<HistoryBuyPackage>? = null
        apiService = MyApplication.with(activity).getEzlearnService()
        mSubscription = apiService!!.getHistoryBuyPackage(page, 5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseListResult<HistoryBuyPackage>>() {
                    override fun onCompleted() {
                        if (mBaseListResult!!.success && mBaseListResult!!.data != null
                                && mBaseListResult?.data!!.list != null
                                && mBaseListResult?.data!!.list!!.isNotEmpty()) {
                            adapterHistoryBuyPackage.addAll(mBaseListResult?.data!!.list!!)
                        }
                    }

                    override fun onError(e: Throwable) = Unit

                    override fun onNext(historyResult: BaseListResult<HistoryBuyPackage>?) {
                        if (historyResult != null) {
                            mBaseListResult = historyResult
                        }
                    }
                })
    }

    private fun getListHistoryPayment(page: Int) {
        var mBaseListResult: BaseListResult<HistoryPayment>? = null
        apiService = MyApplication.with(activity).getEzlearnService()
        mSubscription = apiService!!.getHistoryPayment(page, 5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseListResult<HistoryPayment>>() {
                    override fun onCompleted() {
                        if (mBaseListResult!!.success && mBaseListResult!!.data != null
                                && mBaseListResult?.data!!.list != null
                                && mBaseListResult?.data!!.list!!.isNotEmpty()) {
                            adapterHistoryPayment.addAll(mBaseListResult?.data!!.list!!)
                        }
                    }

                    override fun onError(e: Throwable) = Unit

                    override fun onNext(historyResult: BaseListResult<HistoryPayment>?) {
                        if (historyResult != null) {
                            mBaseListResult = historyResult
                        }
                    }
                })
    }

    companion object {
        private val TYPE_HISTORY = "TYPE_HISTORY"
        val TYPE_EXAM = 1
        val TYPE_PAYMENT = 2
        val TYPE_BUY_PACKAGE = 3
        val TYPE_TRANSACTION = 4
        fun newInstance(typeHistory: Int): HistoryExamFragment {
            val fragment = HistoryExamFragment()
            val args = Bundle()
            args.putInt(TYPE_HISTORY, typeHistory)
            fragment.arguments = args
            return fragment
        }
    }


}

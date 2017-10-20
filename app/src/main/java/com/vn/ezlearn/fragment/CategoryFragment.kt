package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.ExamsAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentCategoryBinding
import com.vn.ezlearn.modelresult.ExamsResult
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class CategoryFragment : Fragment() {

    private var categoryBinding: FragmentCategoryBinding? = null
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null

    private var type_category: Int = 0
    private var category_id: Int = 0
    private var mExamsResult: ExamsResult? = null
    private var adapter: ExamsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            type_category = arguments.getInt(TYPE_CATEGORY)
            category_id = arguments.getInt(CATEGORY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        categoryBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_category, container, false)
        bindData()
        return categoryBinding!!.root
    }

    private fun bindData() {
        adapter = ExamsAdapter(activity, ArrayList())
        categoryBinding!!.rvListExam.adapter = adapter
        getDataApi(1)
    }

    private fun getDataApi(page: Int) {
        apiService = MyApplication.with(activity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.getListExams(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ExamsResult>() {
                    override fun onCompleted() {
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult!!.data!!.list != null
                                && mExamsResult!!.data!!.list!!.size > 0) {
                            adapter!!.addAll(mExamsResult!!.data!!.list!!)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(examsResult: ExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }

    override fun onDetach() {
        super.onDetach()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }

    companion object {

        val TYPE_BAI_GIANG = 1
        val TYPE_DE_THI = 2
        val TYPE_LUYEN_TAP = 3
        private val TYPE_CATEGORY = "TYPE_CATEGORY"
        private val CATEGORY_ID = "CATEGORY_ID"

        fun newInstance(type_category: Int, category_id: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt(TYPE_CATEGORY, type_category)
            args.putInt(CATEGORY_ID, category_id)
            fragment.arguments = args
            return fragment
        }
    }
}

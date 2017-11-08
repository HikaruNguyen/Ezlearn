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
import com.vn.ezlearn.modelresult.ListDocumentResult
import com.vn.ezlearn.modelresult.ListExamsResult
import com.vn.ezlearn.models.ContentByCategory
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CategoryFragment : Fragment() {

    private var categoryBinding: FragmentCategoryBinding? = null
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null

    private var category_id: Int = 0
    private var contentType: Int = 0
    private var mExamsResult: ListExamsResult? = null
    private var mDocumentsResult: ListDocumentResult? = null
    private lateinit var adapter: ExamsAdapter
    private lateinit var list: MutableList<ContentByCategory>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            category_id = arguments.getInt(CATEGORY_ID)
            contentType = arguments.getInt(CONTENT_TYPE)
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
        list = ArrayList()
        adapter = ExamsAdapter(activity, ArrayList())

        categoryBinding!!.rvListExam.adapter = adapter
        getDataApi(1, contentType)
    }

    private fun getDataApi(page: Int, contentType: Int) {
        apiService = MyApplication.with(activity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        when (contentType) {
            ContentByCategory.CONTENT_TYPE_EXAM -> getDataExam(page)
            ContentByCategory.CONTENT_TYPE_DOCUMENT -> getDataDocument(page)
        }

    }

    private fun getDataDocument(page: Int) {
        mSubscription = apiService!!.getListDocument(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListDocumentResult>() {
                    override fun onCompleted() {
                        if (mDocumentsResult!!.success && mDocumentsResult!!.data != null
                                && mDocumentsResult!!.data != null
                                && mDocumentsResult!!.data!!.isNotEmpty()) {
                            mDocumentsResult?.data!!
                                    .map { ContentByCategory(
                                            exam = null, document = it, contentType = contentType) }
                                    .forEach { list.add(it) }

                            adapter.addAll(list)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(documentResult: ListDocumentResult?) {
                        if (documentResult != null) {
                            mDocumentsResult = documentResult
                        }
                    }
                })
    }

    private fun getDataExam(page: Int) {
        mSubscription = apiService!!.getListExams(category_id, page, 50)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult?.data?.list != null
                                && mExamsResult?.data?.list!!.isNotEmpty()) {
                            mExamsResult?.data?.list!!
                                    .map { ContentByCategory(
                                            exam = it, document = null, contentType = contentType) }
                                    .forEach { list.add(it) }

                            adapter.addAll(list)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(examsResult: ListExamsResult?) {
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
        private val CATEGORY_ID = "CATEGORY_ID"
        private val CONTENT_TYPE = "CONTENT_TYPE"

        fun newInstance(category_id: Int, contentType: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt(CATEGORY_ID, category_id)
            args.putInt(CONTENT_TYPE, contentType)
            fragment.arguments = args
            return fragment
        }
    }
}

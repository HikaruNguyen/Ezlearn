package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ExamsAdapter
import com.vn.ezlearn.config.AppConstant
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityListExamBinding
import com.vn.ezlearn.modelresult.ListExamsResult
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.viewmodel.BaseViewModel
import com.vn.ezlearn.widgets.CRecyclerView
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListExamActivity : BaseActivity() {

    private lateinit var listExamBinding: ActivityListExamBinding
    private lateinit var list: MutableList<ContentByCategory>
    private lateinit var adapter: ExamsAdapter
    private lateinit var apiService: EzlearnService
    private lateinit var listExamViewModel: BaseViewModel
    private var mSubscription: Subscription? = null

    private var isLoadingMore: Boolean = false
    private var id: Int = AppConstant.FREE_ID
    private var totalPost: Int = 0
    private var currentPage: Int = 1
    private var mExamsResult: ListExamsResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listExamBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_exam)
        listExamViewModel = BaseViewModel(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        listExamBinding.baseViewModel = listExamViewModel
        getDataIntent()
        bindData()
        event()
    }

    private fun getDataIntent() {
        id = intent.getIntExtra(KEY_ID, AppConstant.FREE_ID)
        when (id) {
            AppConstant.FREE_ID -> supportActionBar!!.title = getString(R.string.nav_free_exam)
            AppConstant.TRY_EXAM_ID -> supportActionBar!!.title = getString(R.string.try_exam)
            AppConstant.REAL_EXAM_ID -> supportActionBar!!.title = getString(R.string.real_exam)
            else -> supportActionBar!!.title = getString(R.string.nav_free_exam)
        }
    }

    private fun event() {
        listExamBinding.rvListExam.loadMore(object : CRecyclerView.LoadMoreListener {
            override fun onScrolled() {
                if (!isLoadingMore && adapter.itemCount < totalPost) {
                    isLoadingMore = true
                    getDataApi(currentPage)
                }
            }
        })
    }

    private fun bindData() {
        list = ArrayList()
        adapter = ExamsAdapter(this, ArrayList())
        listExamBinding.rvListExam.adapter = adapter
        apiService = MyApplication.with(this).getEzlearnService()
        getDataApi(currentPage)
    }

    private fun getDataApi(page: Int) {
        val dataSubs: Observable<ListExamsResult> = when (id) {
            AppConstant.FREE_ID -> apiService.getListFreeExams(page, 50)
            AppConstant.TRY_EXAM_ID -> apiService.getListTryExams(AppConstant.TRY_EXAM, page, 50)
            AppConstant.REAL_EXAM_ID -> apiService.getListTryExams(AppConstant.REAL_EXAM, page, 50)
            else -> apiService.getListFreeExams(page, 50)
        }
        mSubscription = dataSubs
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() =
                            if (mExamsResult!!.success && mExamsResult!!.data != null) {
                                totalPost = mExamsResult?.data!!.totalCount
                                if (mExamsResult?.data?.list != null
                                        && mExamsResult?.data?.list!!.isNotEmpty()) {
                                    listExamViewModel.hideErrorView()
                                    mExamsResult?.data?.list!!
                                            .map {
                                                ContentByCategory(it, null, ContentByCategory.CONTENT_TYPE_EXAM)
                                            }
                                            .forEach { list.add(it) }
                                    currentPage++
                                    adapter.addAll(list)
                                } else {
                                    if (adapter.itemCount <= 0) {
                                        listExamViewModel.setErrorNodata()
                                    } else {

                                    }

                                }
                            } else {
                                if (adapter.itemCount <= 0) {
                                    listExamViewModel.setErrorNodata()
                                } else {

                                }
                            }

                    override fun onError(e: Throwable) {
                        listExamViewModel.setErrorNetwork()
                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }

    companion object {
        val KEY_ID = "KEY_ID"
    }
}

package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.adapter.HomeAdapter
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.config.AppConstant
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentHomeBinding
import com.vn.ezlearn.modelresult.BannerResult
import com.vn.ezlearn.modelresult.ListExamsResult
import com.vn.ezlearn.models.Banner
import com.vn.ezlearn.models.Category
import com.vn.ezlearn.models.HomeObject
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.viewmodel.HomeViewModel
import com.vn.ezlearn.widgets.MyPullToRefresh
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


/**
 * A simple [Fragment] subclass.
 */

class HomeFragment : Fragment(), BaseSliderView.OnSliderClickListener, MyPullToRefresh.OnRefreshBegin {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var list: List<HomeObject>? = null
    private var bannerList: MutableList<Banner>? = null

    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null

    private var mBannerResult: BannerResult? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        homeBinding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_home, container, false)
        homeViewModel = HomeViewModel(activity)
        homeBinding.homeViewModel = homeViewModel
        initList()
        bindData()
        event()
        return homeBinding.root
    }

    private fun event() {
        homeBinding.ptrLoading.setOnRefreshBegin(homeBinding.rvHome, MyPullToRefresh.PullToRefreshHeader(context), this)

    }

    private fun initList() {
        list = ArrayList()
        homeAdapter = HomeAdapter(activity, ArrayList())
        homeBinding.rvHome.adapter = homeAdapter
    }

    override fun refresh() {
        bindData()
    }

    private fun bindData() {
        homeAdapter.clear()
        apiService = MyApplication.with(activity).getEzlearnService()
        if (AppUtils.isNetworkAvailable(activity)) {
            homeViewModel.hideErrorView()
            initBanner()
            getListExamFree(1)
            getListTryExam(1)
            getListRealExam(1)
//            getListBySelectLevel(1)
        } else {
            if (homeBinding.ptrLoading.isRefreshing) {
                homeBinding.ptrLoading.refreshComplete()
            }
            homeViewModel.setErrorNetwork()
        }
    }

    private fun getListBySelectLevel(page: Int) {
        var mExamsResult: ListExamsResult? = null
        mSubscription = apiService!!.getListExams(AppConfig.getInstance(activity).degreeID, page, 3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult!!.data!!.list != null
                                && mExamsResult!!.data!!.list!!.isNotEmpty()) {
                            for (category: Category in MyApplication.with(activity).categoryResult?.data!!) {
                                if (category.category_id.toInt() == AppConfig.getInstance(activity).studyLevelID) {
//                                    homeAdapter.add(HomeObject(category.category_name, ))
                                    for (i in 0 until mExamsResult!!.data!!.list!!.size) {
                                        homeAdapter.add(HomeObject(
                                                mExamsResult!!.data!!.list!![i]))
                                    }
                                    break
                                }
                            }


                        }
                    }

                    override fun onError(e: Throwable) {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })

    }

    private fun getListExamFree(page: Int) {
        var mExamsResult: ListExamsResult? = null
        mSubscription = apiService!!.getListFreeExams(page, 3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult!!.data!!.list != null
                                && mExamsResult!!.data!!.list!!.isNotEmpty()) {
                            homeAdapter.add(
                                    HomeObject(getString(R.string.nav_free_exam), AppConstant.FREE_ID))
                            for (i in 0 until mExamsResult!!.data!!.list!!.size) {
                                homeAdapter.add(HomeObject(
                                        mExamsResult!!.data!!.list!![i]))
                            }

                        }
                    }

                    override fun onError(e: Throwable) {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }

    private fun getListTryExam(page: Int) {
        var mExamsResult: ListExamsResult? = null
        mSubscription = apiService!!.getListTryExams(AppConstant.TRY_EXAM, page, 3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult!!.data!!.list != null
                                && mExamsResult!!.data!!.list!!.isNotEmpty()) {
                            homeAdapter.add(
                                    HomeObject(getString(R.string.try_exam), AppConstant.TRY_EXAM_ID))
                            for (i in 0 until mExamsResult!!.data!!.list!!.size) {
                                homeAdapter.add(HomeObject(
                                        mExamsResult!!.data!!.list!![i]))
                            }

                        }
                    }

                    override fun onError(e: Throwable) {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }

    private fun getListRealExam(page: Int) {
        var mExamsResult: ListExamsResult? = null
        mSubscription = apiService!!.getListTryExams(AppConstant.REAL_EXAM, page, 3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ListExamsResult>() {
                    override fun onCompleted() {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                        if (mExamsResult!!.success && mExamsResult!!.data != null
                                && mExamsResult!!.data!!.list != null
                                && mExamsResult!!.data!!.list!!.isNotEmpty()) {
                            homeAdapter.add(
                                    HomeObject(getString(R.string.real_exam), AppConstant.REAL_EXAM_ID))
                            for (i in 0 until mExamsResult!!.data!!.list!!.size) {
                                homeAdapter.add(HomeObject(
                                        mExamsResult!!.data!!.list!![i]))
                            }

                        }
                    }

                    override fun onError(e: Throwable) {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                    }

                    override fun onNext(examsResult: ListExamsResult?) {
                        if (examsResult != null) {
                            mExamsResult = examsResult
                        }
                    }
                })
    }


    private fun initBanner() {
        bannerList = ArrayList()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.banners
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BannerResult>() {
                    override fun onCompleted() {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                        if (mBannerResult!!.success && mBannerResult!!.data != null
                                && mBannerResult!!.data!!.isNotEmpty()) {
                            for (i in 0 until mBannerResult!!.data!!.size) {
                                bannerList!!.add(Banner(mBannerResult!!.data!![i]))
                            }
                            homeAdapter.add(0, HomeObject(bannerList!!))
                            homeBinding.rvHome.smoothScrollToPosition(0)
                        }

                    }

                    override fun onError(e: Throwable) {
                        if (homeBinding.ptrLoading.isRefreshing) {
                            homeBinding.ptrLoading.refreshComplete()
                        }
                    }

                    override fun onNext(bannerResult: BannerResult?) {
                        if (bannerResult != null) {
                            mBannerResult = bannerResult
                        }
                    }
                })
    }


    override fun onSliderClick(slider: BaseSliderView) {

    }

    override fun onDetach() {
        super.onDetach()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }
}// Required empty public constructor

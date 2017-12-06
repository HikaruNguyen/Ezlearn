package com.vn.ezlearn.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vn.ezlearn.R
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivitySplashBinding
import com.vn.ezlearn.modelresult.BaseResult
import com.vn.ezlearn.models.Category
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private lateinit var mCategoryResult: BaseResult<Category>
    private var isAttach = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        loadDataCategory()
    }

    private fun loadDataCategory() {
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.category
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseResult<Category>>() {
                    override fun onCompleted() {
                        if (isAttach && mCategoryResult.success) {
                            mCategoryResult.data?.let {
                                it.forEach { category ->
                                    if (category.children != null
                                            && category.children!!.isNotEmpty()) {
                                        for (categoryChild in category.children!!) {
                                            if (categoryChild.children != null
                                                    && categoryChild.children!!.isNotEmpty()) {
                                                category.levelChild = Category.LEVEL_3
                                                category.typeMenu = Category.TYPE_PARENT
                                            } else {
                                                category.levelChild = Category.LEVEL_2
                                                category.typeMenu = Category.TYPE_NORMAL
                                            }
                                        }
                                    } else {
                                        category.levelChild = Category.LEVEL_1
                                        category.typeMenu = Category.TYPE_NORMAL
                                    }
                                }
                            }

                            MyApplication.with(this@SplashActivity).categoryResult = mCategoryResult
//                            if (!AppConfig.getInstance(this@SplashActivity).isSelectLevel) {
//                                val intent = Intent(this@SplashActivity, SelectLevelActivity::class.java)
//                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                startActivity(intent)
//                                finish()
//                            } else {
//                                goToMain()
//                            }
                            goToMain()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttach) {
                            goToMain()
                        }

                    }

                    override fun onNext(categoryResult: BaseResult<Category>?) {
                        if (categoryResult != null) {
                            mCategoryResult = categoryResult
                        }
                    }
                })
    }

    private inner class Task : Runnable {
        override fun run() {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            goToMain()
        }


    }

    private fun goToMain() {
        val intent = Intent(this@SplashActivity, Main2Activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        isAttach = false
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }
}

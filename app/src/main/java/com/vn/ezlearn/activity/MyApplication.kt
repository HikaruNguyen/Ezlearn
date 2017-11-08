package com.vn.ezlearn.activity

import android.app.Application
import android.content.Context
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.modelresult.CategoryResult

/**
 * Created by manhi on 4/1/2017.
 */

class MyApplication : Application() {
    var categoryResult: CategoryResult? = null

    fun getEzlearnService(): EzlearnService = EzlearnService.Factory.create(this)

    companion object {
        fun with(context: Context): MyApplication = context.applicationContext as MyApplication
    }
}

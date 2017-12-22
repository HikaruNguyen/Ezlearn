package com.vn.ezlearn.activity

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityCheckOrderBinding
import com.vn.ezlearn.nganluong.ErrorCodePayment
import com.vn.ezlearn.nganluong.bank.modelResult.CheckOrderResult
import com.vn.ezlearn.nganluong.bank.utils.DefineCodeBank
import com.vn.ezlearn.utils.AppUtils
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CheckOrderActivity : BaseActivity() {
    private lateinit var checkOrderBinding: ActivityCheckOrderBinding
    private lateinit var mTokenCode: String
    private var mSubscription: Subscription? = null
    private lateinit var apiService: EzlearnService
    private lateinit var progressLoading: ProgressDialog
    private var mCheckOrderResult: CheckOrderResult? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_order)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mTokenCode = intent.getStringExtra(TOKEN_CODE)
        apiService = MyApplication.with(this).getEzlearnService()
        checkOrderObject()
    }

    private fun checkOrderObject() {
        progressLoading = ProgressDialog.show(this, "", getString(R.string.loading), false)
        val stringSendOrder = DefineCodeBank.FUNC_CHECKORDER + "|" +
                DefineCodeBank.VERSION + "|" +
                BuildConfig.MERCHANT_ID + "|" +
                mTokenCode + "|" +
                BuildConfig.MERCHANT_PASS
        val checksum = AppUtils.md5(stringSendOrder)
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.checkOderRequest(DefineCodeBank.FUNC_CHECKORDER, DefineCodeBank.VERSION,
                BuildConfig.MERCHANT_ID, mTokenCode, checksum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<CheckOrderResult>() {
                    override fun onCompleted() {
                        mCheckOrderResult?.let {
                            val errorCode = it.response_code!!
                            Log.i("log", errorCode)
                            if (Integer.parseInt(errorCode) == 0) {
                                val dataCheckOrder = "response_code:  " + it.response_code + "\n\n" +
                                        "receiver_email:  " +  it.receiver_email + "\n\n" +
                                        "order_code:  " +  it.order_code + "\n\n" +
                                        "total_amount:  " +  it.total_amount + "\n\n" +
                                        "currency:  " +  it.currency + "\n\n" +
                                        "language:  " +  it.language + "\n\n" +
                                        "return_url:  " +  it.return_url + "\n\n" +
                                        "cancel_url:  " +  it.cancel_url + "\n\n" +
                                        "notify_url:  " +  it.notify_url + "\n\n" +
                                        "buyer_full_name:  " +  it.buyer_fullname + "\n\n" +
                                        "buyer_email:  " +  it.buyer_email + "\n\n" +
                                        "buyer_mobile:  " +  it.buyer_mobile + "\n\n" +
                                        "buyer_address:  " +  it.buyer_address + "\n\n" +
                                        "transaction_id:  " +  it.transaction_id + "\n\n" +
                                        "transaction_status:  " +  it.transaction_status + "\n\n" +
                                        "transaction_amount:  " + it. transaction_amount + "\n\n" +
                                        "transaction_currency:  " +  it.transaction_currency + "\n\n" +
                                        "transaction_escrow:  " +  it.transaction_escrow + "\n\n"

                                checkOrderBinding.checkorderTxtData.text = dataCheckOrder
                                checkOrderBinding.checkorderTxtData.setVisibility(View.VISIBLE)

                            } else {
                                val builder = AlertDialog.Builder(this@CheckOrderActivity)
                                builder.apply {
                                    setTitle(getString(R.string.error_code, errorCode))
                                    setMessage(ErrorCodePayment.getInstance(this@CheckOrderActivity)
                                            .listErrorCodeBank.getValue(Integer.parseInt(errorCode)))
                                    setCancelable(false)
                                    setPositiveButton(getString(R.string.ok), { dialog, _ ->
                                        dialog.dismiss()
                                        finish()
                                    })
                                    show()
                                }
                            }

                        }

                    }


                    override fun onError(e: Throwable) {
                        progressLoading.dismiss()
                        val builder = AlertDialog.Builder(this@CheckOrderActivity)
                        builder.apply {
                            setTitle(getString(R.string.error))
                            setMessage(getString(R.string.error_connect))
                            setCancelable(false)
                            setPositiveButton(getString(R.string.ok), { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            })
                            show()
                        }
                    }

                    override fun onNext(checkOrderResult: CheckOrderResult?) {
                        if (progressLoading.isShowing) {
                            progressLoading.dismiss()
                        }
                        if (checkOrderResult != null) {
                            mCheckOrderResult = checkOrderResult
                        }
                    }
                })

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val TOKEN_CODE = "token_code"
    }
}

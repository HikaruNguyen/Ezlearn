package com.vn.ezlearn.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.CheckOutActivity
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.FragmentPaymentByBankBinding
import com.vn.ezlearn.nganluong.DefineCodeCard
import com.vn.ezlearn.nganluong.ErrorCodePayment
import com.vn.ezlearn.nganluong.bank.modelResult.SendOrderResult
import com.vn.ezlearn.nganluong.bank.utils.DefineCodeBank
import com.vn.ezlearn.utils.AppUtils
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * A simple [Fragment] subclass.
 */
class PaymentByBankFragment : Fragment() {

    private lateinit var paymentByBankBinding: FragmentPaymentByBankBinding
    private var mSubscription: Subscription? = null
    private var isAttach = true
    private lateinit var apiService: EzlearnService
    private lateinit var progressLoading: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        paymentByBankBinding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_payment_by_bank,
                container, false)
        apiService = MyApplication.with(activity).getEzlearnService()
        paymentByBankBinding.btnSendOrder.setOnClickListener {
            payment()
        }
        return paymentByBankBinding.root
    }

    private fun payment() {
        with(paymentByBankBinding) {
            val fullName = edFullName.text.toString()
            val amount = edAmount.text.toString()
            val email = edEmail.text.toString()
            val phoneNumber = edPhoneNumber.text.toString()
            val address = edAddress.text.toString()

            if (!fullName.equals("", ignoreCase = true)) {
                if (!amount.equals("", ignoreCase = true) && Integer.valueOf(amount) >= 2000) {
                    if (!email.equals("", ignoreCase = true)) {
                        if (!phoneNumber.equals("", ignoreCase = true)) {
                            if (!address.equals("", ignoreCase = true)) {
                                sendOrderObject(fullName, amount, email, phoneNumber, address)
                            } else {
                                Toast.makeText(activity, getString(R.string.error_address), Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(activity, getString(R.string.error_mobile), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(activity, getString(R.string.error_email), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.error_amount), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, getString(R.string.error_name_order), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendOrderObject(fullName: String, amount: String, email: String,
                                phoneNumber: String, address: String) {
        progressLoading = ProgressDialog.show(activity, "", getString(R.string.loading), false)
        val stringSendOrder = DefineCodeBank.FUNC + "|" + DefineCodeBank.VERSION + "|" +
                BuildConfig.MERCHANT_ID + "|" + DefineCodeCard.RECEIVER + "|" + "123456DEMO" + "|" +
                amount + "|" + DefineCodeBank.CURRENCY + "|" + DefineCodeBank.LANGUAGE + "|" +
                DefineCodeBank.RETURN_URL + "|" + DefineCodeBank.CANCEL_URL + "|" +
                DefineCodeBank.NOTIFY_URL + "|" +
                fullName + "|" + email + "|" + phoneNumber + "|" + address + "|" +
                BuildConfig.MERCHANT_PASS

        val checksum = AppUtils.md5(stringSendOrder)
        var mSendOrderResult: SendOrderResult? = null
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.sendOderRequest(DefineCodeBank.FUNC, DefineCodeBank.VERSION,
                BuildConfig.MERCHANT_ID, DefineCodeCard.RECEIVER, "123456DEMO", amount,
                DefineCodeBank.CURRENCY, DefineCodeBank.LANGUAGE, DefineCodeBank.RETURN_URL,
                DefineCodeBank.CANCEL_URL, DefineCodeBank.NOTIFY_URL, fullName, email, phoneNumber, address, checksum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<SendOrderResult>() {
                    override fun onCompleted() {
                        mSendOrderResult?.let {
                            val errorCode = it.response_code!!
                            Log.i("log", errorCode)
                            if (Integer.parseInt(errorCode) == 0) {
                                val intent = Intent(activity, CheckOutActivity::class.java)
                                intent.putExtra(CheckOutActivity.TOKEN_CODE, it.token_code)
                                intent.putExtra(CheckOutActivity.CHECKOUT_URL, it.checkout_url)
                                startActivity(intent)

                            } else {
                                val builder = AlertDialog.Builder(activity)
                                builder.apply {
                                    setTitle(getString(R.string.error_code, errorCode))
                                    setMessage(ErrorCodePayment.getInstance(activity)
                                            .listErrorCodeBank.getValue(Integer.parseInt(errorCode)))
                                    setCancelable(false)
                                    setPositiveButton(getString(R.string.ok), { dialog, _ -> dialog.dismiss() })
                                    show()
                                }
                            }

                        }

                    }


                    override fun onError(e: Throwable) {
                        if (isAttach && progressLoading.isShowing) {
                            progressLoading.dismiss()
                        }
                    }

                    override fun onNext(sendOrderResult: SendOrderResult?) {
                        if (isAttach && progressLoading.isShowing) {
                            progressLoading.dismiss()
                        }
                        if (sendOrderResult != null) {
                            mSendOrderResult = sendOrderResult
                        }
                    }
                })

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
        isAttach = false
    }

    override fun onResume() {
        super.onResume()
        isAttach = true
    }

    override fun onDetach() {
        super.onDetach()
        isAttach = false
    }
}

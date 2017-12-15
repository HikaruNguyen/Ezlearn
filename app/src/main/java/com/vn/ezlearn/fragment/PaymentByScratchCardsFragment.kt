package com.vn.ezlearn.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.activity.PaymentActivity
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.FragmentPaymentByScratchCardsBinding
import com.vn.ezlearn.nganluong.DefineCodeCard
import com.vn.ezlearn.nganluong.ErrorCodeCard
import com.vn.ezlearn.utils.AppUtils
import okhttp3.ResponseBody
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * A simple [Fragment] subclass.
 */
class PaymentByScratchCardsFragment : Fragment() {
    private lateinit var paymentByScratchCardsBinding: FragmentPaymentByScratchCardsBinding
    private lateinit var apiService: EzlearnService
    private var mSubscription: Subscription? = null
    private var selected_card_type = DefineCodeCard.VIETTEL
    private var paymentResult: String? = null
    private lateinit var progressLoading: ProgressDialog
    private var isAttach = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        paymentByScratchCardsBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_payment_by_scratch_cards, container, false)
        apiService = MyApplication.with(activity).getEzlearnService()
        paymentByScratchCardsBinding.btnPayment.setOnClickListener {
            if (UserConfig.getInstance(activity).isLogined()) {
                progressLoading = ProgressDialog.show(activity, "", getString(R.string.loading), false)
                paymentByScratchCardsBinding.btnPayment.isEnabled = false
                payment()
            } else {
                val intent = Intent(activity, PaymentActivity::class.java)
                startActivity(intent)
            }
        }
        event()
        return paymentByScratchCardsBinding.root
    }

    private fun payment() {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.paymentByScartchCard(DefineCodeCard.FUNC, DefineCodeCard.VERSION,
                BuildConfig.MERCHANT_ID, DefineCodeCard.RECEIVER,
                AppUtils.md5(BuildConfig.MERCHANT_ID + "|" + BuildConfig.MERCHANT_PASS),
                paymentByScratchCardsBinding.cardcode.text.toString(),
                paymentByScratchCardsBinding.cardserial.text.toString(), selected_card_type,
                UserConfig.getInstance(activity).id.toString(), UserConfig.getInstance(activity).name,
                UserConfig.getInstance(activity).email, "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ResponseBody>() {
                    override fun onCompleted() {
                        paymentByScratchCardsBinding.btnPayment.isEnabled = true
                        if (paymentResult != null && !paymentResult!!.isEmpty()) {
                            val params = paymentResult?.split("\\|".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()!!
                            val errorCode = params[0]
                            Log.i("log", errorCode)
//                            if (Integer.parseInt(errorCode) == 0) {
//                                val cardAmount = params[10]
//                                val builder1 = AlertDialog.Builder(activity)
//                                builder1.setMessage("Thành công gía trị thẻ nạp " + cardAmount)
//                                builder1.setCancelable(true)
//                                builder1.setPositiveButton("OK",
//                                        { dialog, _ -> dialog.cancel() })
//                                val alert11 = builder1.create()
//                                alert11.show()
//                            } else {
//                                val builder1 = AlertDialog.Builder(activity)
//                                builder1.setMessage("Lỗi - mã lỗi  " + errorCode)
//                                builder1.setCancelable(true)
//                                builder1.setPositiveButton("OK",
//                                        { dialog, _ -> dialog.cancel() })
//                                val alert11 = builder1.create()
//                                alert11.show()
//                            }
                            val builder = AlertDialog.Builder(activity)
                            builder.apply {
                                setTitle(if (Integer.parseInt(errorCode) == 0) {
                                    getString(R.string.download_success)
                                } else {
                                    getString(R.string.error_code, errorCode)
                                })
                                setMessage(ErrorCodeCard.instance.errorCode[Integer.parseInt(errorCode)])
                                setCancelable(false)
                                setPositiveButton(getString(R.string.ok), { dialog, _ -> dialog.dismiss() })
                                show()
                            }
                        }
                    }


                    override fun onError(e: Throwable) {
                        paymentByScratchCardsBinding.btnPayment.isEnabled = true
                        if (isAttach && progressLoading.isShowing) {
                            progressLoading.dismiss()
                        }
                    }

                    override fun onNext(responseBody: ResponseBody?) {
                        if (isAttach && progressLoading.isShowing) {
                            progressLoading.dismiss()
                        }
                        if (responseBody != null) {
                            paymentResult = responseBody.string()
                        }
                    }
                })
    }

    private fun event() {
        paymentByScratchCardsBinding.rgCard.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.ViettelButton ->
                    selected_card_type = DefineCodeCard.VIETTEL
                R.id.VinaPhoneButton ->
                    selected_card_type = DefineCodeCard.VINAPHONE
                R.id.MobiFoneButton ->
                    selected_card_type = DefineCodeCard.MOBIFONE
            }
        }
    }

    private fun getdata(_url: String) = try {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val url = URL(_url)
        val con = url
                .openConnection() as HttpURLConnection
        readStream(con.getInputStream())
    } catch (e: Exception) {
        e.printStackTrace()
    }

    private fun readStream(`in`: InputStream) {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(`in`))
            var content = ""
            var line: String?
            line = reader.readLine()
            while (line != null) {
                println(line)
                content += line
                line = reader.readLine()
            }
            val params = content.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val error_code = params[0]
            Log.i("log", error_code)
            if (Integer.parseInt(error_code) == 0) {
                val card_amount = params[10]
                val builder1 = AlertDialog.Builder(activity)
                builder1.setMessage("Thành công gía trị thẻ nạp " + card_amount)
                builder1.setCancelable(true)
                builder1.setPositiveButton("OK",
                        { dialog, _ -> dialog.cancel() })
                val alert11 = builder1.create()
                alert11.show()
            } else {
                val builder1 = AlertDialog.Builder(activity)
                builder1.setMessage("Lỗi - mã lỗi  " + error_code)
                builder1.setCancelable(true)
                builder1.setPositiveButton("OK",
                        { dialog, _ -> dialog.cancel() })
                val alert11 = builder1.create()
                alert11.show()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
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

}// Required empty public constructor

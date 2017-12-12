package com.vn.ezlearn.fragment


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
import com.vn.ezlearn.databinding.FragmentPaymentByScratchCardsBinding
import com.vn.ezlearn.utils.AppUtils
import com.vn.ezlearn.utils.CLog
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
    private var selected_card_type = "VIETTEL"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        paymentByScratchCardsBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_payment_by_scratch_cards, container, false)
        with(paymentByScratchCardsBinding) {
            button1.setOnClickListener {
                CLog.i("log", cardcode.text.toString())
                CLog.i("log", cardserial.text.toString())
                CLog.i("Log", selected_card_type)

                val merchant_id = BuildConfig.MERCHANT_ID
                val password = BuildConfig.MERCHANT_PASS
                val mail = BuildConfig.RECEIVER
                val merchant_password = AppUtils.md5(merchant_id + "|" + password)
                var q = ""
                q = "func=CardCharge&version=2.0&merchant_id=" + merchant_id + "&merchant_account=" + mail + "&merchant_password=" + merchant_password +
                        "&pin_card=" + cardcode.getText().toString() + "&card_serial=" + cardserial.text.toString() + "&client_fullname=Do%20Cong%20Dien&client_email=diendc@gmail.com&client_mobile=0904515105"
                val URL = "https://www.nganluong.vn/mobile_card.api.post.v2.php?" + q
                Log.i("log", URL)
                getdata(URL)
            }
        }

        return paymentByScratchCardsBinding.root
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


}// Required empty public constructor

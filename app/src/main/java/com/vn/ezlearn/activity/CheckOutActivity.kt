package com.vn.ezlearn.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import com.vn.ezlearn.R
import com.vn.ezlearn.databinding.ActivityCheckOutBinding
import com.vn.ezlearn.nganluong.bank.utils.DefineCodeBank
import com.vn.ezlearn.widgets.CustomWebView

class CheckOutActivity : BaseActivity(), CustomWebView.MyWebViewClientListener {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if (url.equals(DefineCodeBank.RETURN_URL, ignoreCase = true)) {
            val intentCheckOut = Intent(applicationContext, CheckOrderActivity::class.java)
            intentCheckOut.putExtra(CheckOrderActivity.TOKEN_CODE, mTokenCode)
            startActivity(intentCheckOut)
            finish()
        }
    }

    override fun onPageFinished(view: WebView, url: String) {

    }

    private lateinit var checkOutBinding: ActivityCheckOutBinding
    private lateinit var mTokenCode: String
    private lateinit var mCheckoutUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOutBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_out)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mTokenCode = intent.getStringExtra(TOKEN_CODE)
        mCheckoutUrl = intent.getStringExtra(CHECKOUT_URL)
        if (!mCheckoutUrl.equals("", ignoreCase = true)) {
            checkOutBinding.webview.myWebViewClientListener = this
            checkOutBinding.webview.loadUrl(mCheckoutUrl)
        }
    }

    companion object {
        val TOKEN_CODE = "token_code"
        val CHECKOUT_URL = "checkout_url"
    }
}

package com.vn.ezlearn.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pnikosis.materialishprogress.ProgressWheel


/**
 * Created by manhi on 31/1/2016.
 */
class CustomWebView : WebView {
    var type: Int = 0
    var answer: Int = 0

    var progressWheel: ProgressWheel? = null

    var position: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        this.webViewClient = MyBrowser()
        val webSettings = this.settings
        webSettings.loadsImagesAutomatically = true
        webSettings.javaScriptEnabled = true
        //        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.loadWithOverviewMode = true

        webSettings.userAgentString = "Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) " +
                "AppleWebKit/<WebKit Rev> (KHTML, like Gecko) " +
                "Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>6"

    }

    private inner class MyBrowser : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
            //            prbLoading.setVisibility(View.VISIBLE);
            if (progressWheel != null) {
                progressWheel!!.visibility = View.VISIBLE
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (progressWheel != null) {
                progressWheel!!.visibility = View.GONE
            }

        }


    }
}

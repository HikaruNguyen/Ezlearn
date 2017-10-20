package com.vn.ezlearn.activity

import android.app.Activity
import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityLoginBinding
import com.vn.ezlearn.modelresult.LoginResult
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginActivity : BaseActivity() {
    private var loginBinding: ActivityLoginBinding? = null
    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private var mLoginResult: LoginResult? = null
    private var progressDialog: ProgressDialog? = null
    private var isAttach = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(loginBinding!!.toolbar)
        setBackButtonToolbar()
        loginBinding!!.toolbar.title = ""
        loginBinding!!.btnLogin.setOnClickListener {
            val username = loginBinding!!.edUserName.text.toString()
            val password = loginBinding!!.edPassword.text.toString()
            when {
                username.isEmpty() -> Toast.makeText(this@LoginActivity,
                        getString(R.string.please_input_username),
                        Toast.LENGTH_SHORT).show()
                password.isEmpty() -> Toast.makeText(this@LoginActivity,
                        getString(R.string.please_input_password),
                        Toast.LENGTH_SHORT).show()
                else -> login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false)
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.getLogin(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<LoginResult>() {
                    override fun onCompleted() {
                        if (mLoginResult!!.success && mLoginResult!!.data != null) {
                            if (!mLoginResult!!.data!!.message.isEmpty()) {
                                Toast.makeText(this@LoginActivity, mLoginResult!!.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@LoginActivity, getString(R.string.login_success),
                                        Toast.LENGTH_SHORT).show()
                            }
                            AppConfig.getInstance(this@LoginActivity).token = mLoginResult!!.data!!.access_token!!
                            AppConfig.getInstance(this@LoginActivity).name = mLoginResult!!.data!!.display_name!!
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, getString(R.string.error_connect),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttach && progressDialog!!.isShowing) {
                            progressDialog!!.dismiss()
                        }
                    }

                    override fun onNext(loginResult: LoginResult?) {
                        if (isAttach && progressDialog!!.isShowing) {
                            progressDialog!!.dismiss()
                        }
                        if (loginResult != null) {
                            mLoginResult = loginResult
                        }
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        isAttach = false
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }

    companion object {
        val LOGIN_REQUEST = 11
    }
}

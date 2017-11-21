package com.vn.ezlearn.activity

import android.app.Activity
import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.ActivityLoginBinding
import com.vn.ezlearn.modelresult.LoginResult
import com.vn.ezlearn.modelresult.UserInfoResult
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var apiService: EzlearnService
    private var mSubscription: Subscription? = null
    private lateinit var mLoginResult: LoginResult
    private lateinit var progressDialog: ProgressDialog
    private var isAttach = true
    private lateinit var mUserInfoResult: UserInfoResult
    private var isErrorValidate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(loginBinding.toolbar)
        setBackButtonToolbar()
        loginBinding.toolbar.title = ""
        loginBinding.btnLogin.setOnClickListener {
            val username = loginBinding.edUserName.text.toString()
            val password = loginBinding.edPassword.text.toString()
            validateEmpty(username, password)
            if (!isErrorValidate) {
                login(username, password)
            }
        }
    }

    private fun validateEmpty(username: String, password: String) {
        isErrorValidate = true
        if (username.isEmpty() && password.isEmpty()) {
            loginBinding.tplUserName.error = getString(R.string.please_input_username)
            loginBinding.tplPassword.error = getString(R.string.please_input_password)
        } else if (username.isEmpty()) {
            loginBinding.tplUserName.error = getString(R.string.please_input_username)
            loginBinding.tplPassword.error = null
        } else if (password.isEmpty()) {
            loginBinding.tplPassword.error = getString(R.string.please_input_password)
            loginBinding.tplUserName.error = null
        } else {
            loginBinding.tplUserName.error = null
            loginBinding.tplPassword.error = null
            isErrorValidate = false
        }

    }


    private fun login(username: String, password: String) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false)
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.getLogin(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<LoginResult>() {
                    override fun onCompleted() {
                        if (mLoginResult.success && mLoginResult.data != null) {
                            if (!mLoginResult.data!!.message?.isEmpty()!!) {
                                Toast.makeText(this@LoginActivity, mLoginResult.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@LoginActivity, getString(R.string.login_success),
                                        Toast.LENGTH_SHORT).show()
                            }
                            UserConfig.getInstance(this@LoginActivity).token =
                                    mLoginResult.data!!.access_token!!
                            UserConfig.getInstance(this@LoginActivity).name =
                                    mLoginResult.data!!.display_name!!
                            getUserProfile()
                        } else if (mLoginResult.data != null) {
                            if (!mLoginResult.data!!.message?.isEmpty()!!) {
                                Toast.makeText(this@LoginActivity, mLoginResult.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@LoginActivity, getString(R.string.error_progress),
                                        Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, getString(R.string.error_progress),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttach && progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                    }

                    override fun onNext(loginResult: LoginResult?) {
                        if (isAttach && progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        if (loginResult != null) {
                            mLoginResult = loginResult
                        }
                    }
                })
    }

    private fun getUserProfile() {
        apiService = MyApplication.with(this@LoginActivity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.getUserInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<UserInfoResult>() {
                    override fun onCompleted() {
                        if (mUserInfoResult.success!! && mUserInfoResult.data != null) {
                            if (mUserInfoResult.data?.user_packages != null &&
                                    mUserInfoResult.data?.user_packages?.size!! > 0) {
                                if (mUserInfoResult.data?.user_packages?.get(0)?.code_name != null) {
                                    UserConfig.getInstance(this@LoginActivity).user_package =
                                            mUserInfoResult.data?.user_packages?.get(0)?.code_name!!
                                }
                            }
                            if (mUserInfoResult.data?.user != null) {
                                if (mUserInfoResult.data?.user?.wallet != null) {
                                    UserConfig.getInstance(this@LoginActivity).wallet =
                                            mUserInfoResult.data?.user!!.wallet!!
                                }

                            }
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }

                    override fun onError(e: Throwable) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }

                    override fun onNext(userInfoResult: UserInfoResult?) {
                        if (userInfoResult != null) {
                            mUserInfoResult = userInfoResult
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

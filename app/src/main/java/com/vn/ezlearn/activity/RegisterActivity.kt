@file:Suppress("DEPRECATION")

package com.vn.ezlearn.activity

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityRegisterBinding
import com.vn.ezlearn.modelresult.LoginResult
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterActivity : BaseActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private var isErrorValidate = false
    private lateinit var apiService: EzlearnService
    private var mSubscription: Subscription? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mRegisterResult: LoginResult
    private var isAttach = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setSupportActionBar(registerBinding.toolbar)
        setBackButtonToolbar()
        registerBinding.toolbar.title = ""
        registerBinding.btnRegister.setOnClickListener {
            val firstName = registerBinding.edFirstName.text.toString()
            val lastName = registerBinding.edLastName.text.toString()
            val username = registerBinding.edUserName.text.toString()
            val password = registerBinding.edPassword.text.toString()
            val rePassword = registerBinding.edRePassword.text.toString()
            validateEmpty(firstName, lastName, username, password, rePassword)
            if (isErrorValidate) {
                return@setOnClickListener
            } else {
                validatePassword(password, rePassword)
                if (isErrorValidate) {
                    return@setOnClickListener
                } else {
                    register(firstName, lastName, username, password)
                }
            }
        }
    }

    private fun register(firstName: String, lastName: String, username: String,
                         password: String) {
        progressDialog = ProgressDialog.show(this@RegisterActivity, "",
                getString(R.string.loading), true, false)
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.postRegister(username = username, password = password,
                firstName = firstName, lastName = lastName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<LoginResult>() {
                    override fun onCompleted() {
                        if (mRegisterResult.success && mRegisterResult.data != null) {
                            if (!mRegisterResult.data!!.message?.isEmpty()!!) {
                                Toast.makeText(this@RegisterActivity, mRegisterResult.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@RegisterActivity, getString(R.string.register_success),
                                        Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        } else if (mRegisterResult.data != null) {
                            if (!mRegisterResult.data!!.message?.isEmpty()!!) {
                                Toast.makeText(this@RegisterActivity, mRegisterResult.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@RegisterActivity, getString(R.string.error_progress),
                                        Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@RegisterActivity, getString(R.string.error_progress),
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
                            mRegisterResult = loginResult
                        }
                    }
                })
    }

    private fun validatePassword(password: String, rePassword: String) {
        if (password.contentEquals(rePassword)) {
            isErrorValidate = false
            registerBinding.tilRePassword.error = null
        } else {
            registerBinding.tilRePassword.error = getString(R.string.re_password_not_correct)
            isErrorValidate = true
        }
    }

    private fun validateEmpty(firstName: String, lastName: String, username: String,
                              password: String, rePassword: String) {
        isErrorValidate = false
        if (firstName.isEmpty()) {
            registerBinding.tilFirstName.error = getString(R.string.please_input_first_name)
            isErrorValidate = true
        } else {
            registerBinding.tilFirstName.error = null
        }
        if (lastName.isEmpty()) {
            registerBinding.tilLastName.error = getString(R.string.please_input_last_name)
            isErrorValidate = true
        } else {
            registerBinding.tilLastName.error = null
        }
        if (username.isEmpty()) {
            registerBinding.tilUserName.error = getString(R.string.please_input_username)
            isErrorValidate = true
        } else {
            registerBinding.tilUserName.error = null
        }
        if (password.isEmpty()) {
            registerBinding.tilPassword.error = getString(R.string.please_input_password)
            isErrorValidate = true
        } else {
            registerBinding.tilPassword.error = null
        }
        if (rePassword.isEmpty()) {
            registerBinding.tilRePassword.error = getString(R.string.please_input_password)
            isErrorValidate = true
        } else {
            registerBinding.tilRePassword.error = null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        isAttach = false
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }

    companion object {
        val LOGIN_REQUEST = 11
        val REGISTER_REQUEST = 12
    }
}

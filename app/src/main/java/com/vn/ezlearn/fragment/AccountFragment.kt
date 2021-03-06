@file:Suppress("DEPRECATION")

package com.vn.ezlearn.fragment


import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.*
import com.vn.ezlearn.config.AppConstant
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.FragmentAccountBinding
import com.vn.ezlearn.modelresult.CommonResult
import com.vn.ezlearn.viewmodel.AccountViewModel
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment(), View.OnClickListener {


    private lateinit var accountBinding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var apiService: EzlearnService
    private var mSubscription: Subscription? = null
    private var commonResultLogout: CommonResult? = null
    private lateinit var progressLoading: ProgressDialog
    private var isAttach = true
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        accountBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_account, container, false)
        accountViewModel = AccountViewModel(activity)
        accountBinding.accountViewModel = accountViewModel
        event()
        return accountBinding.root
    }

    private fun event() {
        with(accountBinding) {
            rlLogin.setOnClickListener(this@AccountFragment)
            lnHistoryExam.setOnClickListener(this@AccountFragment)
            lnHistoryTransaction.setOnClickListener(this@AccountFragment)
            lnLogout.setOnClickListener(this@AccountFragment)
            lnContact.setOnClickListener(this@AccountFragment)
            lnPayment.setOnClickListener(this@AccountFragment)
            lnFeedback.setOnClickListener(this@AccountFragment)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LoginActivity.LOGIN_REQUEST) {
                accountViewModel.updateProfile()
            }
        }
    }


    override fun onClick(v: View?) {
        val intent: Intent
        when (v!!.id) {
            R.id.rlLogin -> {
                if (UserConfig.getInstance(activity).isLogined()) {
                    intent = Intent(activity, UserProfile::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }
            }
            R.id.lnHistoryExam -> {
                if (UserConfig.getInstance(activity).isLogined()) {
                    intent = Intent(activity, AccountActivity::class.java)
                    intent.putExtra(AccountActivity.TYPE_HISTORY, HistoryExamFragment.TYPE_EXAM)
                    startActivity(intent)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }

            }
            R.id.lnHistoryTransaction -> {
                if (UserConfig.getInstance(activity).isLogined()) {
                    intent = Intent(activity, AccountActivity::class.java)
                    intent.putExtra(AccountActivity.TYPE_HISTORY, HistoryExamFragment.TYPE_TRANSACTION)
                    startActivity(intent)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }
            }
            R.id.lnContact -> {
                intent = Intent(activity, AccountActivity::class.java)
                intent.putExtra(AccountActivity.TYPE_FRAGMENT, AccountActivity.TYPE_CONTACT)
                startActivity(intent)
            }
            R.id.lnPayment -> {
                if (UserConfig.getInstance(activity).isLogined()) {
                    intent = Intent(activity, PaymentActivity::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }

            }
            R.id.lnLogout -> {
                val builder = AlertDialog.Builder(activity)
                with(builder){
                    setTitle(getString(R.string.nav_logout))
                    setMessage(getString(R.string.confirm_logout))
                    setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                        run {
                            dialog!!.dismiss()
                            logout()
                        }
                    }
                    setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog!!.dismiss() }
                    show()
                }

            }
            R.id.lnFeedback -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto: " + AppConstant.EMAIL_EZLEARN)
                startActivity(Intent.createChooser(emailIntent, "Send feedback"))
            }
        }
    }

    private fun logout() {
        progressLoading = ProgressDialog.show(activity, "", getString(R.string.loading), false)
        apiService = MyApplication.with(activity).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService.logout
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<CommonResult>() {
                    override fun onCompleted() {
                        if (isAdded && isAttach) {
                            accountViewModel.visiableLogout.set(View.GONE)
                            progressLoading.dismiss()
                            if (commonResultLogout!!.success) {
                                if (commonResultLogout!!.data != null
                                        && !commonResultLogout!!.data!!.message.isEmpty()) {
                                    Toast.makeText(activity,
                                            commonResultLogout!!.data!!.message,
                                            Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(activity, getString(R.string.logout_success),
                                            Toast.LENGTH_SHORT).show()
                                }
                                UserConfig.getInstance(activity).clearData()
                                accountViewModel.updateProfile()
                            } else {
                                Toast.makeText(activity, getString(R.string.error_connect),
                                        Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                    override fun onError(e: Throwable) {
                        if (isAdded && isAttach) {
                            progressLoading.dismiss()
                            Toast.makeText(activity, getString(R.string.error_connect), Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onNext(commonResult: CommonResult?) {
                        if (commonResult != null) {
                            commonResultLogout = commonResult
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

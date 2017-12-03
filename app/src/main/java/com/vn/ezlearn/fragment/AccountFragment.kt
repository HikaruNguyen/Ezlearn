package com.vn.ezlearn.fragment


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.AccountActivity
import com.vn.ezlearn.activity.LoginActivity
import com.vn.ezlearn.activity.MyApplication
import com.vn.ezlearn.activity.UserProfile
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.FragmentAccountBinding
import com.vn.ezlearn.modelresult.BaseResult
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
    private var baseResultLogout: BaseResult? = null
    private lateinit var progressLoading: ProgressDialog
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
        accountBinding.rlLogin.setOnClickListener(this)
        accountBinding.lnHistoryExam.setOnClickListener(this)
        accountBinding.lnHistoryTransaction.setOnClickListener(this)
        accountBinding.lnLogout.setOnClickListener(this)
        accountBinding.lnContact.setOnClickListener(this)

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
                if (!UserConfig.getInstance(activity).token.isEmpty()) {
                    intent = Intent(activity, UserProfile::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }
            }
            R.id.lnHistoryExam -> {
                if (!UserConfig.getInstance(activity).token.isEmpty()) {
                    intent = Intent(activity, AccountActivity::class.java)
                    intent.putExtra(AccountActivity.TYPE_HISTORY, HistoryExamFragment.TYPE_EXAM)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }

            }
            R.id.lnHistoryTransaction -> {
                if (!UserConfig.getInstance(activity).token.isEmpty()) {
                    intent = Intent(activity, AccountActivity::class.java)
                    intent.putExtra(AccountActivity.TYPE_HISTORY, HistoryExamFragment.TYPE_TRANSACTION)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                } else {
                    intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
                }
            }
            R.id.lnContact -> {
                intent = Intent(activity, AccountActivity::class.java)
                intent.putExtra(AccountActivity.TYPE_FRAGMENT, AccountActivity.TYPE_CONTACT)
                startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
            }
            R.id.lnLogout -> {
                logout()
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
                .subscribe(object : Subscriber<BaseResult>() {
                    override fun onCompleted() {
                        accountViewModel.visiableLogout.set(View.GONE)
                        progressLoading.dismiss()
                        if (baseResultLogout!!.success) {
                            if (baseResultLogout!!.data != null
                                    && !baseResultLogout!!.data!!.message.isEmpty()) {
                                Toast.makeText(activity,
                                        baseResultLogout!!.data!!.message,
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

                    override fun onError(e: Throwable) {
                        progressLoading.dismiss()
                    }

                    override fun onNext(baseResult: BaseResult?) {
                        if (baseResult != null) {
                            baseResultLogout = baseResult
                        }
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }
}

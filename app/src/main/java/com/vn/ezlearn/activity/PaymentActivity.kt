package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.ActivityPaymentBinding
import com.vn.ezlearn.fragment.PaymentByScratchCardsFragment

class PaymentActivity : BaseActivity() {
    private lateinit var paymentBinding: ActivityPaymentBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var fragmentList: MutableList<Fragment>
    private lateinit var fragmentListTitle: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        setSupportActionBar(paymentBinding.toolbar!!)
        paymentBinding.toolbar!!.title = getString(R.string.payment)
        setBackButtonToolbar()
        bindData()
    }

    private fun bindData() {
        fragmentList = ArrayList()
        fragmentListTitle = ArrayList()

        fragmentListTitle.add(getString(R.string.buy_service))
        fragmentList.add(PaymentByScratchCardsFragment())

        fragmentListTitle.add(getString(R.string.payment_by_scratch_cards))
        fragmentList.add(PaymentByScratchCardsFragment())

        fragmentListTitle.add(getString(R.string.payment_by_bank))
        fragmentList.add(PaymentByScratchCardsFragment())



        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList,
                fragmentListTitle)
        paymentBinding.viewPager.adapter = viewPagerAdapter
        paymentBinding.tabs.setupWithViewPager(
                paymentBinding.viewPager)
    }
}

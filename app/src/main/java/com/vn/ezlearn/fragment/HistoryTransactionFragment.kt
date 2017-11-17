package com.vn.ezlearn.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.databinding.FragmentHistoryTransactionBinding
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class HistoryTransactionFragment : Fragment() {

    private lateinit var historyTransactionBinding: FragmentHistoryTransactionBinding
    private lateinit var fragmentList: MutableList<Fragment>
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        historyTransactionBinding = DataBindingUtil.inflate(inflater!!,
                R.layout.fragment_history_transaction, container, false)
        bindData()
        event()
        return historyTransactionBinding.root
    }

    private fun bindData() {
        fragmentList = ArrayList()
        fragmentList.add(HistoryExamFragment())
        fragmentList.add(HistoryExamFragment.newInstance(HistoryExamFragment.TYPE_BUY_PACKAGE))
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, fragmentList)
        historyTransactionBinding.viewPager.adapter = viewPagerAdapter

    }

    private fun event() {
        historyTransactionBinding.rgTab.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPayment -> switchPayment()
                R.id.rbBuyPackage -> switchBuyPackage()
            }
        }

        historyTransactionBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> historyTransactionBinding.rbPayment.isChecked = true
                    1 -> historyTransactionBinding.rbBuyPackage.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun switchBuyPackage() {
        historyTransactionBinding.rbBuyPackage.setTextColor(
                ContextCompat.getColor(activity, R.color.white))
        historyTransactionBinding.rbPayment.setTextColor(
                ContextCompat.getColor(activity, R.color.colorPrimaryDark))
        historyTransactionBinding.viewPager.currentItem = 1
    }

    private fun switchPayment() {
        historyTransactionBinding.rbPayment.setTextColor(
                ContextCompat.getColor(activity, R.color.white))
        historyTransactionBinding.rbBuyPackage.setTextColor(
                ContextCompat.getColor(activity, R.color.colorPrimaryDark))
        historyTransactionBinding.viewPager.currentItem = 0
    }

}

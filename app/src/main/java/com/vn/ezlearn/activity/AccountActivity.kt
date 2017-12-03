package com.vn.ezlearn.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.vn.ezlearn.R
import com.vn.ezlearn.databinding.ActivityHistoryBinding
import com.vn.ezlearn.fragment.ContactFragment
import com.vn.ezlearn.fragment.HistoryExamFragment
import com.vn.ezlearn.fragment.HistoryTransactionFragment

class AccountActivity : BaseActivity() {
    private lateinit var historyBinding: ActivityHistoryBinding
    private var typeHistory: Int? = null
    private var typeContact: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyBinding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        bindData()
    }

    private fun bindData() {
        typeContact = intent.getStringExtra(TYPE_FRAGMENT)
        if (typeContact != null) {
            if (typeContact!!.contentEquals(TYPE_CONTACT)) {
                changeFragment(ContactFragment())
                supportActionBar!!.title = getString(R.string.nav_contact)
            } else if (typeContact!!.contentEquals(TYPE_EXAM)) {

            }
        } else {
            typeHistory = intent.getIntExtra(TYPE_HISTORY, HistoryExamFragment.TYPE_EXAM)
            if (typeHistory != null) {
                if (typeHistory == HistoryExamFragment.TYPE_TRANSACTION) {
                    changeFragment(HistoryTransactionFragment())
                    supportActionBar!!.title = getString(R.string.tab_history_transaction)
                } else {
                    changeFragment(HistoryExamFragment.newInstance(typeHistory!!))
                    when (typeHistory) {
                        HistoryExamFragment.TYPE_EXAM ->
                            supportActionBar!!.title = getString(R.string.tab_history_exam)
                    }
                }
            }

        }
    }

    private fun changeFragment(targetFragment: Fragment) {
        clearBackStack(supportFragmentManager)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameHistory, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    private fun clearBackStack(manager: FragmentManager) {
        if (manager.backStackEntryCount > 0) {
            val first = manager
                    .getBackStackEntryAt(0)
            manager.popBackStack(first.id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }


    companion object {
        val TYPE_FRAGMENT = "TYPE_FRAGMENT"
        val TYPE_HISTORY = "TYPE_HISTORY"
        val TYPE_CONTACT = "TYPE_CONTACT"
        val TYPE_EXAM = "TYPE_EXAM"
    }
}

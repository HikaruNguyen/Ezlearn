package com.vn.ezlearn.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.vn.ezlearn.R


/**
 * Created by Nguyen Duc Manh on 14/8/2015.
 */
abstract class BaseActivity : AppCompatActivity() {
    private var mActionBarToolbar: Toolbar? = null

    val toolbar: Toolbar
        get() {
            if (mActionBarToolbar == null) {
                mActionBarToolbar = findViewById(R.id.toolbar) as Toolbar

            }
            return mActionBarToolbar as Toolbar
        }

    fun setBackButtonToolbar() {
        mActionBarToolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }
}

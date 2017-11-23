package com.vn.ezlearn.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.SpinListAdapter
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.databinding.ActivitySelectLevelBinding
import com.vn.ezlearn.models.Category
import java.util.*


class SelectLevelActivity : AppCompatActivity() {
    private lateinit var selectLevelBinding: ActivitySelectLevelBinding
    private lateinit var studyLevelAdapter: SpinListAdapter
    private lateinit var degreeAdapter: SpinListAdapter

    private lateinit var listStudyLevel: MutableList<String>
    private lateinit var listDegreeLevel: MutableList<String>

    private var studyLevelPosition = 0
    private var degreePosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectLevelBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_select_level)
        bindData()
        event()
    }

    private fun event() {
        selectLevelBinding.spnStudyLevel.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i != studyLevelPosition) {
                    studyLevelPosition = i
                    degreePosition = 0
                    loadDegree(studyLevelPosition)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                studyLevelPosition = 0
            }
        }
        selectLevelBinding.spnDegree.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i != degreePosition) {
                    degreePosition = i
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                studyLevelPosition = 0
            }
        }
        selectLevelBinding.btnOk.setOnClickListener {
            AppConfig.getInstance(this).isSelectLevel = true
            AppConfig.getInstance(this).studyLevelID =
                    MyApplication.with(this).categoryResult?.data!![studyLevelPosition].category_id.toInt()
            AppConfig.getInstance(this).degreeID =
                    MyApplication.with(this).categoryResult?.data!![studyLevelPosition]
                            .children!![degreePosition].category_id.toInt()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun bindData() {
        if (MyApplication.with(this).categoryResult != null
                && MyApplication.with(this).categoryResult?.data != null
                && MyApplication.with(this).categoryResult?.data?.size!! > 0) {
            loadStudyLevel()
            loadDegree(studyLevelPosition)
        }

    }

    private fun loadDegree(studyLevelPosition: Int) {
        listDegreeLevel = ArrayList()
        for (category: Category in MyApplication.with(this)
                .categoryResult?.data!![studyLevelPosition].children!!) {
            listDegreeLevel.add(category.category_name)
        }
        degreeAdapter = SpinListAdapter(this,
                R.layout.item_select_level, listDegreeLevel)
        selectLevelBinding.spnDegree.adapter = degreeAdapter
    }

    private fun loadStudyLevel() {
        listStudyLevel = ArrayList()
        for (category: Category in MyApplication.with(this).categoryResult?.data!!) {
            listStudyLevel.add(category.category_name)
        }
        studyLevelAdapter = SpinListAdapter(this,
                R.layout.item_select_level, listStudyLevel)
        selectLevelBinding.spnStudyLevel.adapter = studyLevelAdapter


    }
}

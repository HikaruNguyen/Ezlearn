package com.vn.ezlearn.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.DialogListQuestionAdapter
import com.vn.ezlearn.databinding.ActivityShowPointBinding
import com.vn.ezlearn.models.MyContent
import com.vn.ezlearn.utils.CLog
import com.vn.ezlearn.utils.QuestionUtils
import com.vn.ezlearn.viewmodel.ShowPointViewModel
import java.util.*

class ShowPointActivity : AppCompatActivity() {
    private lateinit var showPointBinding: ActivityShowPointBinding
    private lateinit var showPointViewModel: ShowPointViewModel
    private var point: Float = 0.toFloat()
    private var numAnswerCorrect: Int = 0
    private var numAnswerNoCorrect: Int = 0
    private var numNoAnswer: Int = 0

    private lateinit var myContentList: List<MyContent>

    private var widthLinePoint: Int = 0
    private var widthPoint: Int = 0
    private var widthRlPoint = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showPointBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_point)
        getData()
        setProgressPoint()
        bindListQuestion()
        event()
    }

    private fun event() {
        showPointBinding.btnReviewAnswer.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun bindListQuestion() {
        val layoutManager = GridLayoutManager(this, 7)
        showPointBinding.rvQuestion.layoutManager = layoutManager
        showPointBinding.rvQuestion.setHasFixedSize(true)
        showPointBinding.rvQuestion.itemAnimator = DefaultItemAnimator()

        val listQuestionAdapter = DialogListQuestionAdapter(
                this, ArrayList())
        showPointBinding.rvQuestion.adapter = listQuestionAdapter
        listQuestionAdapter.addAll(myContentList)
        showPointBinding.scrollView.fullScroll(ScrollView.FOCUS_UP)
    }


    private fun getData() {
        val name = intent.getStringExtra(KEY_NAME)
        val hours = intent.getIntExtra(KEY_HOURS, 0)
        val minutes = intent.getIntExtra(KEY_MINUTES, 0)
        val seconds = intent.getIntExtra(KEY_SECONDS, 0)
        myContentList = QuestionUtils.instance.myContentList!!
        for (myContent in myContentList) {
            if (myContent.typeQuestion == MyContent.TYPE_NO_ANSWER) {
                numNoAnswer++
            } else {
                if (myContent.isCorrect) {
                    point += myContent.point!!
                    numAnswerCorrect++
                }
            }
        }
        CLog.d(TAG," MyPoint: "+point)
        numAnswerNoCorrect = myContentList.size - numNoAnswer - numAnswerCorrect
        showPointViewModel = ShowPointViewModel(this, point, numAnswerCorrect,
                numAnswerNoCorrect, numNoAnswer, hours, minutes, seconds, name)
        showPointBinding.showPointViewModel = showPointViewModel
    }


    @SuppressLint("RtlHardcoded")
    private fun setProgressPoint() {

        showPointBinding.linePoint.post {
            widthLinePoint = showPointBinding.linePoint.width

            widthPoint = (widthLinePoint / 10 * point).toInt()
            Log.d(TAG, "widthLinePoint: $widthLinePoint $widthPoint")


            showPointBinding.lnPoint.post {
                widthRlPoint = showPointBinding.lnPoint.width
                val layoutParams = LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT)
                val marginLeftReal = widthPoint - widthRlPoint / 2
                Log.d(TAG, "marginLeftReal: $marginLeftReal $widthRlPoint")
                if (marginLeftReal > 0) {

                    if (widthPoint + widthRlPoint / 2 > widthLinePoint) {
                        layoutParams.gravity = Gravity.RIGHT
                    } else {
                        layoutParams.setMargins(marginLeftReal, 0, 0, 0)
                    }
                } else {
                    layoutParams.setMargins(0, 0, 0, 0)
                }

                when {
                    point <= 0 -> showPointBinding.lnPoint.setBackgroundResource(
                            R.drawable.bg_point_left)
                    point >= 10 -> showPointBinding.lnPoint.setBackgroundResource(
                            R.drawable.bg_point_right)
                    else -> showPointBinding.lnPoint.setBackgroundResource(
                            R.drawable.bg_point_center)
                }
                showPointBinding.lnPoint.layoutParams = layoutParams

                showPointBinding.ivTriangle.post {
                    val widthIv = showPointBinding.ivTriangle.width
                    val heightIv = showPointBinding.ivTriangle.height
                    val lpTriangle = LinearLayout.LayoutParams(widthIv, heightIv)
                    Log.d(TAG, "lpTriangle: " + widthIv + " " + (widthPoint - widthIv / 2))
                    lpTriangle.setMargins(widthPoint - widthIv / 2, 0, 0, 0)
                    showPointBinding.ivTriangle.layoutParams = lpTriangle
                }
            }
        }
    }

    companion object {
        private val TAG = ShowPointActivity::class.java.simpleName
        val KEY_REQUEST = 22
        val KEY_NAME = "KEY_NAME"
        val KEY_HOURS = "KEY_HOURS"
        val KEY_MINUTES = "KEY_MINUTES"
        val KEY_SECONDS = "KEY_SECONDS"
    }
}

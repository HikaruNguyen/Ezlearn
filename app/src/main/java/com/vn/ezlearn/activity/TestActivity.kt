package com.vn.ezlearn.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.DialogListQuestionAdapter
import com.vn.ezlearn.adapter.QuestionObjectAdapter
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityTestBinding
import com.vn.ezlearn.databinding.DialogListAnswerBinding
import com.vn.ezlearn.interfaces.ChangeQuestionListener
import com.vn.ezlearn.interfaces.OnCheckAnswerListener
import com.vn.ezlearn.interfaces.OnClickQuestionPopupListener
import com.vn.ezlearn.modelresult.QuestionResult
import com.vn.ezlearn.models.Answer
import com.vn.ezlearn.models.MyContent
import com.vn.ezlearn.models.Question
import com.vn.ezlearn.models.QuestionObject
import com.vn.ezlearn.utils.QuestionUtils
import com.vn.ezlearn.viewmodel.TestViewModel
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class TestActivity : BaseActivity(), ChangeQuestionListener, OnCheckAnswerListener,
        OnClickQuestionPopupListener {
    private var testBinding: ActivityTestBinding? = null
    private var testViewModel: TestViewModel? = null
    private var adapter: QuestionObjectAdapter? = null
    private var list: MutableList<QuestionObject>? = null
    private var contentList: MutableList<MyContent>? = null
    private var dialogListAnswer: AlertDialog? = null
    private var countDownTimer: CountDownTimer? = null
    private val time = (15 * 60 * 1000).toLong()

    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null
    private var mQuestionResult: QuestionResult? = null
    private var id: Int = 0
    private var name: String? = null

    private var isAttach = true
    private var progressDialog: ProgressDialog? = null

    private var seconds: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        setSupportActionBar(testBinding!!.toolbar)
        getIntentData()
        initUI()
        bindData()
        event()
    }

    private fun event() {
        testBinding!!.imgNext.setOnClickListener { adapter!!.onNextButton() }

        testBinding!!.imgPre.setOnClickListener { adapter!!.onPrevButton() }
    }

    private fun getIntentData() {
        id = intent.getIntExtra(KEY_ID, 0)
        name = intent.getStringExtra(KEY_NAME)
    }

    private fun initUI() {
        setBackButtonToolbar()
        testViewModel = TestViewModel(this, getString(R.string.app_name))
        testBinding!!.testViewModel = testViewModel
    }

    private fun bindData() {
        adapter = QuestionObjectAdapter(this, ArrayList(), this, this)
        testBinding!!.rvList.adapter = adapter
        list = ArrayList()
        contentList = ArrayList()
        callDataApi()
    }

    private fun callDataApi() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false)
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.getContentExam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<QuestionResult>() {
                    override fun onCompleted() {
                        if (mQuestionResult!!.success && mQuestionResult!!.data != null
                                && mQuestionResult!!.data!!.data != null
                                && mQuestionResult!!.data!!.data!!.size > 0) {
                            for (question in mQuestionResult!!.data!!.data!!) {
                                if (question.region != null) {
                                    val point: Float? = if (question.region!!.number_question == 0) {
                                        question.region!!.total_mark /
                                                question.region!!.number_question
                                    } else {
                                        0f
                                    }
                                    if (question.type != null) {
                                        if (question.type == Question.TYPE_QUESTION) {
                                            if (question.question != null
                                                    && question.question!!.isNotEmpty()) {
                                                question.question!!
                                                        .map {
                                                            MyContent(
                                                                    question.region!!,
                                                                    question.type, it,
                                                                    point)
                                                        }
                                                        .forEach { contentList!!.add(it) }
                                            }
                                        } else if (question.type == Question.TYPE_READING) {
                                            if (question.reading != null
                                                    && question.reading!!.isNotEmpty()) {
                                                question.reading!!
                                                        .filter {
                                                            it.questions != null
                                                                    && it.questions!!.isNotEmpty()
                                                        }
                                                        .forEach { reading ->
                                                            reading.questions!!
                                                                    .map {
                                                                        MyContent(
                                                                                question.region!!,
                                                                                question.type, it,
                                                                                reading.content,
                                                                                point)
                                                                    }
                                                                    .forEach { contentList!!.add(it) }
                                                        }
                                            }
                                        }
                                    }
                                }
                            }
                            list!!.add(QuestionObject(contentList!!))
                            list!!.add(0, QuestionObject(
                                    contentList!![0].region.region_code
                                            + " " + contentList!![0].region.description))
                            testViewModel!!.updatePosition(0, contentList!!.size)
                            adapter!!.addAll(list!!)
                            countDown()
                        } else {

                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttach && progressDialog!!.isShowing) {
                            progressDialog!!.dismiss()
                        }
                    }

                    override fun onNext(questionResult: QuestionResult?) {
                        if (isAttach && progressDialog!!.isShowing) {
                            progressDialog!!.dismiss()
                        }
                        if (questionResult != null) {
                            mQuestionResult = questionResult
                        }
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_question, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_review) {
            showPopupListQuestion()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showPopupListQuestion() {
        val builder = AlertDialog.Builder(this)
        val dialogListAnswerBinding = DataBindingUtil.inflate<DialogListAnswerBinding>(
                LayoutInflater.from(this), R.layout.dialog_list_answer, null, false)
        builder.setView(dialogListAnswerBinding.root)

        val layoutManager = GridLayoutManager(this, 6)
        dialogListAnswerBinding.rvlist.layoutManager = layoutManager
        dialogListAnswerBinding.rvlist.setHasFixedSize(true)
        dialogListAnswerBinding.rvlist.itemAnimator = DefaultItemAnimator()

        val listQuestionAdapter = DialogListQuestionAdapter(
                this, ArrayList(), this)
        dialogListAnswerBinding.rvlist.adapter = listQuestionAdapter
        listQuestionAdapter.addAll(contentList!!)
        dialogListAnswerBinding.btnNopBai.setOnClickListener {
            calculateScore()
            dialogListAnswer!!.dismiss()
        }
        dialogListAnswerBinding.btnCancel.setOnClickListener { dialogListAnswer!!.dismiss() }
        builder.setCancelable(false)
        dialogListAnswer = builder.create()
        dialogListAnswer!!.show()
    }

    private fun calculateScore() {
        countDownTimer!!.cancel()
        val hours = seconds / 3600
        val minutes = seconds % 3600 / 60
        seconds = seconds % 60
        QuestionUtils.instance.myContentList = contentList

        val intent = Intent(this, ShowPointActivity::class.java)
        intent.putExtra(ShowPointActivity.KEY_NAME, name)
        intent.putExtra(ShowPointActivity.KEY_HOURS, hours)
        intent.putExtra(ShowPointActivity.KEY_MINUTES, minutes)
        intent.putExtra(ShowPointActivity.KEY_SECONDS, seconds)
        startActivityForResult(intent, ShowPointActivity.KEY_REQUEST)
    }

    override fun onChange(position: Int) {
        if (contentList != null && contentList!!.size > 0) {
            if (list != null && list!!.size > 0) {
                val part = (contentList!![position].region.region_code
                        + " " + contentList!![0].region.description)
                if (adapter!!.getItemByPosition(0).part != part) {
                    adapter!!.setData(0, QuestionObject(contentList!![position].region.region_code
                            + " " + contentList!![0].region.description))
                }
            }
            testViewModel!!.updatePosition(position, contentList!!.size)
        }

    }

    override fun onCheckAnswer(position: Int, answer: Int) {
        if (contentList != null && contentList!!.size > position) {
            val content = contentList!![position]
            if (content.content.answer_list != null && content.content.answer_list!!.size > answer) {
                content.typeQuestion = MyContent.TYPE_ANSWERED
                content.isCorrect = content.content.answer_list!![answer].is_true == Answer.TRUE
                contentList!![position] = content
            }

        }

    }

    override fun onNeedReview(position: Int) {
        if (contentList != null && contentList!!.size > position) {
            val content = contentList!![position]
            content.typeQuestion = MyContent.TYPE_LATE
            contentList!![position] = content
        }
    }

    override fun onClick(position: Int) {
        if (dialogListAnswer!!.isShowing) {
            dialogListAnswer!!.dismiss()
            adapter!!.onMoveQuestion(position)
        }
    }

    private fun countDown() {
        countDownTimer = object : CountDownTimer(time, 1000) { // adjust the milli seconds here

            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                seconds++
                testViewModel!!.title = "" + String.format(
                        FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

            }

            override fun onFinish() {
                testViewModel!!.title = getString(R.string.hetGio)
                calculateScore()
            }
        }
        countDownTimer!!.start()

    }


    override fun onDestroy() {
        super.onDestroy()
        isAttach = false
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ShowPointActivity.KEY_REQUEST) {
                adapter!!.showSuggest()
            }
        } else {
            finish()
        }
    }

    companion object {
        val KEY_ID = "KEY_ID"
        val KEY_NAME = "KEY_NAME"
        private val FORMAT = "%02d:%02d:%02d"
    }
}
package com.vn.ezlearn.fragment

import android.databinding.DataBindingUtil
import android.graphics.Paint
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.databinding.FragmentQuestionBinding
import com.vn.ezlearn.interfaces.OnCheckAnswerListener
import com.vn.ezlearn.models.Answer
import com.vn.ezlearn.models.Content
import com.vn.ezlearn.models.MyContent
import com.vn.ezlearn.viewmodel.QuestionViewModel

@Suppress("DEPRECATION")
/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

class QuestionFragment : Fragment() {
    private lateinit var fragmentQuestionBinding: FragmentQuestionBinding
    private lateinit var questionViewModel: QuestionViewModel
    private var position: Int = 0
    private var size: Int = 0
    private var content: MyContent? = null
    private var onCheckAnswerListener: OnCheckAnswerListener? = null
    var answer = -1
    var inputAnswer = ""

    fun setQuestion(content: MyContent, onCheckAnswerListener: OnCheckAnswerListener) {
        this.content = content
        this.onCheckAnswerListener = onCheckAnswerListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = arguments.getInt(ARG_SECTION_NUMBER)
            size = arguments.getInt(ARG_SIZE_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentQuestionBinding = DataBindingUtil.inflate(
                inflater!!, R.layout.fragment_question, container, false)
        questionViewModel = QuestionViewModel(activity, content, position, size)
        fragmentQuestionBinding.questionViewModel = questionViewModel
        bindData()
        event()
        return fragmentQuestionBinding.root
    }

    private fun bindData() {

        if (content != null) {
            if (content!!.content.file_image != null) {
                Picasso.with(activity).load(BuildConfig.ENDPOINT_DOWNLOAD + content!!.content.file_image).into(fragmentQuestionBinding.imgQuestion)
            }

            when (content?.myAnswer!!) {
                0 -> {
                    fragmentQuestionBinding.rdAnswerA.isChecked = true
                    answer(0)
                }
                1 -> {
                    fragmentQuestionBinding.rdAnswerB.isChecked = true
                    answer(1)
                }
                2 -> {
                    fragmentQuestionBinding.rdAnswerC.isChecked = true
                    answer(2)
                }
                3 -> {
                    fragmentQuestionBinding.rdAnswerD.isChecked = true
                    answer(3)
                }
                else -> {
                }
            }
            if (content!!.isReview) {
                if (content != null) {
                    showSuggest()
                }

            }
            content!!.content.file_audio?.let {
                //                mediaPlayer = MediaPlayer.create(activity,Uri.parse())
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//                fragmentQuestionBinding.webviewAudio.loadDataWithBaseURL("file:///android_asset/",
//                        AudioHtml.genHtmlAudio(BuildConfig.ENDPOINT_DOWNLOAD + content!!.content.file_audio),
//                        "text/html", "UTF-8", null)
            }

        }
    }

    private fun event() {
        fragmentQuestionBinding.rgAnswer.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rdAnswerA -> answer(0)
                R.id.rdAnswerB -> answer(1)
                R.id.rdAnswerC -> answer(2)
                R.id.rdAnswerD -> answer(3)
                else -> {
                }
            }
        }

        fragmentQuestionBinding.tvNeedReview.setOnClickListener {
            questionViewModel.setNeedReview()
            onCheckAnswerListener!!.onNeedReview(position)
        }

        fragmentQuestionBinding.edInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    answer(editable.toString())
                }

            }
        })
    }

    private fun answer(answer: Int) {
        onCheckAnswerListener!!.onCheckAnswer(position, answer, content!!.content.answer_list!![answer].id)
        this.answer = answer
    }

    private fun answer(answer: String) {
        onCheckAnswerListener!!.onInputAnswer(position, answer)
        this.inputAnswer = answer
        questionViewModel.yourInput.set(Html.fromHtml(context.getString(R.string.your_answer, answer)))
    }

    fun showSuggest() {
        questionViewModel.showSuggest()
        questionViewModel.enableInput.set(false)
        if (content?.content?.answer_show!!.contentEquals(Content.ANSWER_SHOW_DEFAULT)) {
            hideButtonRadioButton(fragmentQuestionBinding.rdAnswerA)
            hideButtonRadioButton(fragmentQuestionBinding.rdAnswerB)
            hideButtonRadioButton(fragmentQuestionBinding.rdAnswerC)
            hideButtonRadioButton(fragmentQuestionBinding.rdAnswerD)

            if (content != null && content!!.content.answer_list != null) {
                if (content!!.content.answer_list!!.isNotEmpty()
                        && content!!.content.answer_list!![0].is_true == Answer.TRUE) {
                    setStyleCorrect(fragmentQuestionBinding.rdAnswerA)
                } else if (content!!.content.answer_list!!.size > 1
                        && content!!.content.answer_list!![1].is_true == Answer.TRUE) {
                    setStyleCorrect(fragmentQuestionBinding.rdAnswerB)
                } else if (content!!.content.answer_list!!.size > 2
                        && content!!.content.answer_list!![2].is_true == Answer.TRUE) {
                    setStyleCorrect(fragmentQuestionBinding.rdAnswerC)
                } else if (content!!.content.answer_list!!.size > 3
                        && content!!.content.answer_list!![3].is_true == Answer.TRUE) {
                    setStyleCorrect(fragmentQuestionBinding.rdAnswerD)
                }
                if (answer >= 0) {
                    if (content!!.content.answer_list!![answer].is_true != Answer.TRUE) {
                        when (answer) {
                            0 -> setStyleNoCorrect(fragmentQuestionBinding.rdAnswerA)
                            1 -> setStyleNoCorrect(fragmentQuestionBinding.rdAnswerB)
                            2 -> setStyleNoCorrect(fragmentQuestionBinding.rdAnswerC)
                            3 -> setStyleNoCorrect(fragmentQuestionBinding.rdAnswerD)
                        }
                    } else {
                        when (answer) {
                            0 -> showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerA)
                            1 -> showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerB)
                            2 -> showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerC)
                            3 -> showTickCorrectAnswer(fragmentQuestionBinding.rdAnswerD)
                        }
                    }
                }
            }
        } else if (content!!.content.answer_show!!.contentEquals(Content.ANSWER_SHOW_INPUT)) {
            questionViewModel.visiableInput.set(View.GONE)
            if (content!!.content.answer_list!!.isNotEmpty()) {
                questionViewModel.correctInput.set(
                        Html.fromHtml(content!!.content.answer_list!![0].answer!!
                                .replace("<p>", "").replace("</p>", "")))
                if (Html.fromHtml(content!!.content.answer_list!![0].answer!!).trim().toString()
                        .contentEquals(inputAnswer.trim())) {
                    showTickCorrectAnswer(fragmentQuestionBinding.tvCorrectAnswer)
                }
            }
        }


    }

    private fun setStyleCorrect(radioButton: RadioButton) {
        radioButton.setTextColor(ContextCompat.getColor(activity, R.color.green))
        radioButton.setTypeface(radioButton.typeface, Typeface.BOLD_ITALIC)
    }

    private fun setStyleNoCorrect(radioButton: RadioButton) {
        radioButton.paintFlags = radioButton.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun hideButtonRadioButton(radioButton: RadioButton) {
        radioButton.setButtonDrawable(android.R.color.transparent)
        radioButton.setPadding(0, 0, 0, 0)
    }

    private fun showTickCorrectAnswer(radioButton: RadioButton) {
        val img = ContextCompat.getDrawable(activity, R.drawable.tick)
        img.setBounds(0, 0, 32, 32)
        radioButton.setCompoundDrawables(img, null, null, null)
    }

    private fun showTickCorrectAnswer(textView: TextView) {
        val img = ContextCompat.getDrawable(activity, R.drawable.tick)
        img.setBounds(0, 0, 32, 32)
        textView.setCompoundDrawables(img, null, null, null)
    }

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"
        private val ARG_SIZE_NUMBER = "size_number"

        fun newInstance(position: Int, size: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, position)
            args.putInt(ARG_SIZE_NUMBER, size)
            fragment.arguments = args
            return fragment
        }
    }
}
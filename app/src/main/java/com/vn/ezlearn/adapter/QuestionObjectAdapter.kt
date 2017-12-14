package com.vn.ezlearn.adapter

import android.app.Activity
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.databinding.ItemQuestionPartBinding
import com.vn.ezlearn.databinding.ItemQuestionViewpagerBinding
import com.vn.ezlearn.fragment.QuestionFragment
import com.vn.ezlearn.interfaces.ChangeQuestionListener
import com.vn.ezlearn.interfaces.OnCheckAnswerListener
import com.vn.ezlearn.models.Content
import com.vn.ezlearn.models.QuestionObject
import com.vn.ezlearn.utils.AudioHtml
import java.util.*

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

class QuestionObjectAdapter(private val activity: Activity, list: MutableList<QuestionObject>,
                            private val changeQuestionListener: ChangeQuestionListener,
                            private val onCheckAnswerListener: OnCheckAnswerListener) :
        BaseRecyclerAdapter<QuestionObject, QuestionObjectAdapter.ViewHolder>(activity, list) {
    private val data: List<QuestionObject>
    private var questionFragments: MutableList<Fragment>? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var itemQuestionPartBinding: ItemQuestionPartBinding? = null
    private var itemQuestionViewpagerBinding: ItemQuestionViewpagerBinding? = null

    internal var positionViewPager = 0
    internal var size = 0

    init {
        this.data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == QuestionObject.TYPE_PART) {
            val binding = DataBindingUtil.inflate<ItemQuestionPartBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_question_part,
                    parent, false)
            ViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<ItemQuestionViewpagerBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_question_viewpager,
                    parent, false)
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        if (getItemViewType(position) == QuestionObject.TYPE_PART) {
            itemQuestionPartBinding = holder.itemQuestionPartBinding
            itemQuestionPartBinding?.let {
                with(it) {
                    tvPart.text = item.part
                    if (item.fileAudio.isNullOrEmpty()) {
                        webviewAudio.visibility = View.GONE
                    } else {
                        webviewAudio.loadDataWithBaseURL("file:///android_asset/",
                                AudioHtml.genHtmlAudio(BuildConfig.ENDPOINT_DOWNLOAD + item.fileAudio),
                                "text/html", "UTF-8", null)
                    }

                }
            }


        } else if (getItemViewType(position) == QuestionObject.TYPE_VIEWPAGER) {
            itemQuestionViewpagerBinding = holder.itemQuestionViewpagerBinding
            val contentList = item.list
            size = item.list!!.size
            questionFragments = ArrayList()
            if (contentList != null) {
                for (i in contentList.indices) {
                    val questionFragment = QuestionFragment.newInstance(i, contentList.size)
                    questionFragment.setQuestion(contentList[i], onCheckAnswerListener)
                    questionFragments!!.add(questionFragment)
                }
            }
            viewPagerAdapter = ViewPagerAdapter(
                    (activity as TestActivity).supportFragmentManager, questionFragments!!)
            with(itemQuestionViewpagerBinding!!.container) {
                adapter = viewPagerAdapter
                offscreenPageLimit = item.list!!.size
                addOnPageChangeListener(
                        object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(
                                    position: Int, positionOffset: Float, positionOffsetPixels: Int) =
                                    Unit

                            override fun onPageSelected(position: Int) {
                                changeQuestionListener.onChange(position)
                                positionViewPager = position
                            }

                            override fun onPageScrollStateChanged(state: Int) = Unit
                        })
            }

        }
    }

    fun onNextButton() {
        if (positionViewPager + 1 < size)
            itemQuestionViewpagerBinding!!.container.currentItem = positionViewPager + 1
    }

    fun onPrevButton() {
        if (positionViewPager > 0)
            itemQuestionViewpagerBinding!!.container.currentItem = positionViewPager - 1
    }


    fun destroyAudio() {
        if (itemQuestionPartBinding != null && itemQuestionPartBinding!!.webviewAudio != null)
            itemQuestionPartBinding!!.webviewAudio.destroy()

    }

    override fun getItemViewType(position: Int): Int = data[position].type

    override fun getItemCount(): Int = data.size

    fun onMoveQuestion(position: Int) {
        if (list.size > 1) {
            if (list[1].list != null && list[1].list!!.size > position) {
                itemQuestionViewpagerBinding!!.container.currentItem = position
            }
        }
    }

    fun showSuggest() {
        if (list.size > 1) {
            for (i in 0 until list[1].list!!.size) {
                (questionFragments!![i] as QuestionFragment).showSuggest()
            }
        }
    }

    fun isAnswered(position: Int, answerType: String): Boolean {
        if (answerType.contentEquals(Content.ANSWER_SHOW_DEFAULT)) {
            return (questionFragments!![position] as QuestionFragment).answer >= 0
        } else if (answerType.contentEquals(Content.ANSWER_SHOW_INPUT)) {
            return !(questionFragments!![position] as QuestionFragment).inputAnswer.isEmpty()
        }
        return false
    }

    class ViewHolder : RecyclerView.ViewHolder {
        internal var itemQuestionPartBinding: ItemQuestionPartBinding? = null
        internal var itemQuestionViewpagerBinding: ItemQuestionViewpagerBinding? = null

        constructor(itemQuestionPartBinding: ItemQuestionPartBinding) :
                super(itemQuestionPartBinding.root) {
            this.itemQuestionPartBinding = itemQuestionPartBinding.apply { executePendingBindings() }
//            this.itemQuestionPartBinding!!.executePendingBindings()
            itemView.setOnClickListener { }
        }

        constructor(itemQuestionViewpagerBinding: ItemQuestionViewpagerBinding) :
                super(itemQuestionViewpagerBinding.root) {
            this.itemQuestionViewpagerBinding = itemQuestionViewpagerBinding.apply { executePendingBindings() }
            itemView.setOnClickListener { }
        }
    }
}
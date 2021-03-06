package com.vn.ezlearn.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.DefaultSliderView
import com.vn.ezlearn.R
import com.vn.ezlearn.activity.ListExamActivity
import com.vn.ezlearn.activity.TestActivity
import com.vn.ezlearn.config.UserConfig
import com.vn.ezlearn.databinding.ItemHomeExamsBinding
import com.vn.ezlearn.databinding.ItemHomeHeaderBinding
import com.vn.ezlearn.databinding.ItemHomeSlideBinding
import com.vn.ezlearn.models.Banner
import com.vn.ezlearn.models.HomeObject
import com.vn.ezlearn.viewmodel.ItemExamViewModel

/**
 * Created by FRAMGIA\nguyen.duc.manh on 15/09/2017.
 */

class HomeAdapter(context: Context, list: MutableList<HomeObject>) :
        BaseRecyclerAdapter<HomeObject, HomeAdapter.ViewHolder>(context, list) {
    private val data: List<HomeObject>
    private var isAddedSlide: Boolean = false

    init {
        this.data = list
        isAddedSlide = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            HomeObject.TYPE_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemHomeHeaderBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_home_header, parent, false)
                return ViewHolder(binding)
            }
            HomeObject.TYPE_EXAM -> {
                val binding = DataBindingUtil.inflate<ItemHomeExamsBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_home_exams, parent, false)
                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemHomeSlideBinding>(
                        LayoutInflater.from(parent.context), R.layout.item_home_slide, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        if (getItemViewType(position) == HomeObject.TYPE_SLIDE) {
            val itemHomeSlideBinding = holder.itemHomeSlideBinding
            if (!isAddedSlide) {
                val displayMetrics = mContext.resources.displayMetrics
                val width = displayMetrics.widthPixels
                val height = (width * 485 / 1366).toFloat()
                with(itemHomeSlideBinding) {
                    rlSlide?.layoutParams = LinearLayout.LayoutParams(width, height.toInt())

                    slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
                    slider.setCustomIndicator(customIndicator2)
                    slider.setCustomAnimation(DescriptionAnimation())
                    slider.setDuration(SLIDE_DELAY)

                    for (i in 0 until item.bannerList!!.size) {
                        val textSliderView = DefaultSliderView(mContext)
                        if (item.bannerList!![i].type == Banner.TYPE_DRAWABLE) {
                            textSliderView
                                    .image(item.bannerList!![i].imageDrawable)
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener { }
                        } else {
                            textSliderView
                                    .image(item.bannerList!![i].imageUrl)
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener { }
                        }

                        slider.addSlider(textSliderView)
                    }
                }

                isAddedSlide = true
            }
        } else if (getItemViewType(position) == HomeObject.TYPE_EXAM) {
            val itemHomeExamsBinding = holder.itemHomeExamsBinding
            val itemExamViewModel = ItemExamViewModel(mContext, list[position].exam)
            with(itemHomeExamsBinding) {
                this@with.itemExamViewModel = itemExamViewModel
                lnExam.setOnClickListener {
                    if (UserConfig.getInstance(mContext).isLogined()) {
                        val intent = Intent(mContext, TestActivity::class.java)
                        if (list[position].exam != null) {
                            with(intent){
                                putExtra(TestActivity.KEY_ID, list[position].exam!!.id)
                                putExtra(TestActivity.KEY_NAME, list[position].exam!!.subject_code)
                                putExtra(TestActivity.KEY_TIME, list[position].exam?.time)
                            }

                        }

                        mContext.startActivity(intent)
                    } else {
                        val builder = AlertDialog.Builder(mContext)
                        builder.setMessage(mContext.getString(R.string.needLogin))
                        builder.setPositiveButton(R.string.ok
                        ) { dialogInterface, _ -> dialogInterface.dismiss() }
                        builder.show()
                    }
                }
            }

        } else if (getItemViewType(position) == HomeObject.TYPE_HEADER) {
            val itemHomeHeaderBinding = holder.itemHomeHeaderBinding
            with(itemHomeHeaderBinding) {
                tvName.text = list[position].header
                tvViewAll.text = mContext.getText(R.string.view_all)
                tvViewAll.setOnClickListener {
                    val intent = Intent(mContext, ListExamActivity::class.java)
                    intent.putExtra(ListExamActivity.KEY_ID, item.idHeader)
                    mContext.startActivity(intent)
                }
            }

        }

    }

    override fun getItemViewType(position: Int): Int = data[position].type

    override fun getItemCount(): Int = data.size

    inner class ViewHolder : RecyclerView.ViewHolder {
        lateinit var itemHomeSlideBinding: ItemHomeSlideBinding
        lateinit var itemHomeHeaderBinding: ItemHomeHeaderBinding
        lateinit var itemHomeExamsBinding: ItemHomeExamsBinding

        constructor(itemHomeSlideBinding: ItemHomeSlideBinding) : super(itemHomeSlideBinding.root) {
            this.itemHomeSlideBinding = itemHomeSlideBinding.apply { executePendingBindings() }
            itemView.setOnClickListener { }
        }

        constructor(itemHomeHeaderBinding: ItemHomeHeaderBinding) : super(itemHomeHeaderBinding.root) {
            this.itemHomeHeaderBinding = itemHomeHeaderBinding.apply { executePendingBindings() }
            itemView.setOnClickListener { }
        }

        constructor(itemHomeExamsBinding: ItemHomeExamsBinding) : super(itemHomeExamsBinding.root) {
            this.itemHomeExamsBinding = itemHomeExamsBinding.apply { executePendingBindings() }
        }
    }

    companion object {
        private val SLIDE_DELAY: Long = 5000
    }
}
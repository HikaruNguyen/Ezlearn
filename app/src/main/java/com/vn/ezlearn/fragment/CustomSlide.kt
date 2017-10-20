package com.vn.ezlearn.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vn.ezlearn.R
import com.vn.ezlearn.databinding.FragmentSlideBinding
import com.vn.ezlearn.viewmodel.CustomSlideViewModel


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

class CustomSlide : Fragment() {

    private var isCanMove: Boolean = false
    private var title: String? = null
    private var description: String? = null
    var backgroundColor: Int = 0
    private var buttonColor: Int = 0
    private var image: Int = 0

    private var slideBinding: FragmentSlideBinding? = null
    private var customSlideViewModel: CustomSlideViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            isCanMove = arguments.getBoolean(ARG_IS_CAN_MOVE)
            title = arguments.getString(ARG_TITLE)
            description = arguments.getString(ARG_DESCRIPTION)
            backgroundColor = arguments.getInt(ARG_BACKGROUND_COLOR)
            buttonColor = arguments.getInt(ARG_BUTTON_COLOR)
            image = arguments.getInt(ARG_IMAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        slideBinding = DataBindingUtil.inflate(inflater!!,
                R.layout.fragment_slide, container, false)
        customSlideViewModel = CustomSlideViewModel(title!!, description!!, image, backgroundColor)
        slideBinding!!.customSlideViewModel = customSlideViewModel
        return slideBinding!!.root
    }

    companion object {
        private val ARG_IS_CAN_MOVE = "is_can_move"
        private val ARG_TITLE = "title"
        private val ARG_DESCRIPTION = "description"
        private val ARG_BACKGROUND_COLOR = "background_color"
        private val ARG_BUTTON_COLOR = "button_color"
        private val ARG_IMAGE = "image"


        fun newInstance(isCanMove: Boolean, title: String, description: String,
                        backgroundColor: Int, buttonColor: Int, image: Int): CustomSlide {
            val fragment = CustomSlide()
            val args = Bundle()
            args.putBoolean(ARG_IS_CAN_MOVE, isCanMove)
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_BACKGROUND_COLOR, backgroundColor)
            args.putInt(ARG_BUTTON_COLOR, buttonColor)
            args.putInt(ARG_IMAGE, image)
            fragment.arguments = args
            return fragment
        }
    }
}
package com.vn.ezlearn.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.ViewPagerAdapter
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.databinding.ActivityIntroBinding
import com.vn.ezlearn.fragment.CustomSlide
import com.vn.ezlearn.utils.ColorUtil
import com.vn.ezlearn.viewmodel.IntroViewModel
import java.util.*


/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

class IntroActivity : AppCompatActivity() {
    private lateinit var introBinding: ActivityIntroBinding
    private lateinit var introViewModel: IntroViewModel
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var fragmentList: MutableList<Fragment>
    private var positionViewPager = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = DataBindingUtil.setContentView(this@IntroActivity, R.layout.activity_intro)
        introViewModel = IntroViewModel(this@IntroActivity)
        introBinding.introViewModel = introViewModel
        if (!AppConfig.getInstance(this).isShowIntro) {
            initViewPager()
            event()
        } else {
            AppConfig.getInstance(this@IntroActivity).isShowIntro = true
            val intent = Intent(this@IntroActivity, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun event() {
        introBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                positionViewPager = position
                //                colorStatusBar(((CustomSlide) fragmentList.get(position)).backgroundColor);
                if (position < fragmentList.size - 1) {
                    introViewModel.textNext.set(getString(R.string.next))
                } else {
                    introViewModel.textNext.set(getString(R.string.done))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        introBinding.tvNext.setOnClickListener {
            if (positionViewPager < fragmentList.size - 1) {
                introBinding.viewPager.currentItem = positionViewPager + 1
            } else {
                AppConfig.getInstance(this@IntroActivity).isShowIntro = true
                val intent = Intent(this@IntroActivity, SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        introBinding.tvPrev.setOnClickListener {
            AppConfig.getInstance(this@IntroActivity).isShowIntro = true
            val intent = Intent(this@IntroActivity, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViewPager() {
        fragmentList = ArrayList()
        addSlide(CustomSlide.newInstance(
                true,
                getString(R.string.bank_exams_title),
                getString(R.string.bank_exams_description),
                R.drawable.bg_intro1,
                Color.TRANSPARENT,
                R.mipmap.clouds))
        addSlide(CustomSlide.newInstance(
                true,
                getString(R.string.route_title),
                getString(R.string.route_description),
                R.drawable.bg_intro1,
                Color.TRANSPARENT,
                R.mipmap.route))

        addSlide(CustomSlide.newInstance(
                false,
                getString(R.string.exams_title),
                getString(R.string.exams_description),
                R.drawable.bg_intro1,
                Color.TRANSPARENT,
                R.mipmap.quality))
        pagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        introBinding.viewPager.adapter = pagerAdapter
        introBinding.indicator.setViewPager(introBinding.viewPager)
        colorStatusBar(R.color.first_slide_background)
    }

    private fun addSlide(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    @SuppressLint("NewApi")
    private fun colorStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.parseColor(
                    ColorUtil.changeColorHSB(resources.getString(color)))
        }
    }
}

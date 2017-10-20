package com.vn.ezlearn.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import java.util.*

/**
 * Created by Windows 10 Version 2 on 9/23/2017.
 */

class ViewPagerAdapter : FragmentPagerAdapter {
    private var fragmentList: List<Fragment> = ArrayList()
    private var fragmentListTitle = ArrayList<String>()

    constructor(manager: FragmentManager, fragmentList: List<Fragment>) : super(manager) {
        this.fragmentList = fragmentList
    }

    constructor(manager: FragmentManager, fragmentList: List<Fragment>,
                fragmentListTitle: List<String>) : super(manager) {
        this.fragmentList = fragmentList
        this.fragmentListTitle = fragmentListTitle as ArrayList<String>
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size


    override fun getPageTitle(position: Int): CharSequence {
        return if (fragmentListTitle.size > position) {
            fragmentListTitle[position]
        } else ""
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {

    }
}
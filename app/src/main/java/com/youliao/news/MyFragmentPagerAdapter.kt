package com.youliao.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.youliao.news.fragment.NewsTabFragment
import com.youliao.news.fragment.VideoTabFragment

class MyFragmentPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NewsTabFragment()
            1 -> VideoTabFragment()
            else -> NewsTabFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
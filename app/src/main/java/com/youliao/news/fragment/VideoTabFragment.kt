package com.youliao.news.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youliao.news.R
import com.youliao.sdk.news.ui.NewsFragment

class VideoTabFragment : Fragment() {

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_news, container, false)
        setupNews()
        return root
    }

    private fun setupNews() {
        val transaction = childFragmentManager.beginTransaction()
        val fragment = NewsFragment.newInstance("video")
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}

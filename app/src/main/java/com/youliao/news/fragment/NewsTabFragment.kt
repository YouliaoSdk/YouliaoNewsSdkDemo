package com.youliao.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youliao.news.R
import com.youliao.sdk.news.ui.NewsFragment

class NewsTabFragment : Fragment() {

    private var root: View? = null

    private var fragment: NewsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_news, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        setupNews()
    }

    private fun setupNews() {
        if (!isAdded) {
            return
        }
        if (fragment == null) {
            fragment = childFragmentManager.findFragmentByTag("NewsFragment") as? NewsFragment
        }
        if (fragment == null) {
            fragment = NewsFragment.newInstance("news")
        }
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!, "NewsFragment")
        transaction.commit()
    }
}

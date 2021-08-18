package com.youliao.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.FrameLayout
import android.widget.Toast
import com.tencent.tauth.Tencent
import com.youliao.news.utils.setStatusBarFollowNightMode
import com.youliao.news.view.FinishListener
import com.youliao.news.view.FloatingButton
import com.youliao.sdk.news.data.bean.NewsBean
import com.youliao.sdk.news.provider.WebViewScrollListener
import com.youliao.sdk.news.ui.WebViewFragment
import com.youliao.sdk.news.ui.share.ShareDialog
import com.youliao.sdk.news.ui.share.ShareUtils
import com.youliao.sdk.news.utils.DeviceInfoUtils

class WebViewDemoActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity, newsBean: NewsBean) {
            val intent = Intent(activity, WebViewDemoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("bean", newsBean)
            activity.startActivity(intent)
        }
    }

    private lateinit var mToolbar: Toolbar
    private var mShowShareMenu = true
    private var mUrl: String? = null
    private lateinit var mShareDialog: ShareDialog
    private lateinit var mContainer: FrameLayout

    private var newsBean: NewsBean? = null
    private lateinit var mWebViewFragment: WebViewFragment

    /**
     * 拖动的圆形进度控件
     */
    lateinit var floatActionView: FloatingButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_webview)

        mShareDialog = ShareDialog(this)
        newsBean = intent.getParcelableExtra("bean")
        mUrl = newsBean?.detailUrl
        if (mUrl != null) {
            initView()
            showShareMenu(newsBean?.isAd != true)
            setupWebViewFragment()
        }
        if (newsBean?.isVideo == true && DeviceInfoUtils.isConnectionExpensive(this)) {
            Toast.makeText(
                this,
                com.youliao.sdk.news.R.string.youliao_video_network_tips,
                Toast.LENGTH_LONG
            ).show()
        }
        floatActionView.setProgress()
    }

    private fun setupWebViewFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        mWebViewFragment = WebViewFragment.newInstance(newsBean!!, scrollListener)
        transaction.replace(R.id.fragment_container, mWebViewFragment)
        transaction.commit()
    }

    private fun setToolbar() {
        mToolbar = findViewById(com.youliao.sdk.news.R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener {
            mWebViewFragment.stayReport()
            finish()
        }
        mToolbar.setOnMenuItemClickListener {
            if (it.itemId == com.youliao.sdk.news.R.id.share) {
                runOnUiThread {
                    mShareDialog.show(newsBean)
                }
            }
            true
        }
        setStatusBarFollowNightMode()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.youliao.sdk.news.R.menu.youliao_sdk_webview_menu, menu)
        val shareMenu = menu?.findItem(com.youliao.sdk.news.R.id.share)
        shareMenu?.isVisible = mShowShareMenu
        return super.onCreateOptionsMenu(menu)
    }

    private fun showShareMenu(showShareMenu: Boolean) {
        mShowShareMenu = showShareMenu
        invalidateOptionsMenu()
    }

    private fun initView() {
        setToolbar()
        mContainer = findViewById(R.id.fragment_container)
        floatActionView = findViewById(R.id.float_view)
        floatActionView.setFinishListener(object : FinishListener {
            override fun onFinishListener() {
                // 一圈已转完，在这里加金币
            }
        })
    }

    override fun onBackPressed() {
        if (!mWebViewFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Tencent.onActivityResultData(
            requestCode,
            resultCode,
            data,
            ShareUtils.getQQShareListener(this.applicationContext)
        )
    }

    private val scrollListener = object : WebViewScrollListener {
        override fun scroll() {
            floatActionView.scrollDetected()
        }
    }
}
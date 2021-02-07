package com.youliao.news.utils

import android.app.Activity
import com.youliao.news.R
import com.youliao.sdk.news.utils.StatusBarUtil

fun Activity.setStatusBarFollowNightMode(isFullMode: Boolean = true) {
    val statusWhite = StatusBarUtil.getDarkModeStatus(this)
    StatusBarUtil.setStatusBarColor(
        this,
        R.color.youliao_toolbar_bg
    )
    StatusBarUtil.setLightStatusBar(this, dark = statusWhite, isFullMode = isFullMode)
}
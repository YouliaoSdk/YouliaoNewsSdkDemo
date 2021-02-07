package com.youliao.news.view

import android.content.res.Resources
import android.util.DisplayMetrics

object FloatingUtils {
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 屏幕宽高
     */
    fun getDisplayMetrics(): DisplayMetrics = Resources.getSystem().displayMetrics
}
package com.youliao.news.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

object Utils {
    @JvmStatic
    fun getCurrentProcessName(applicationContext: Context): String {
        val pid = Process.myPid()
        var processName = ""
        val manager: ActivityManager =
            applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }
}
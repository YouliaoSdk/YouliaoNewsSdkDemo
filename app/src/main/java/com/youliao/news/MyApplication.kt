package com.youliao.news

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import android.webkit.WebView
import com.youliao.sdk.location.AMapLocationProvider
import com.youliao.sdk.news.YouliaoNewsSdk
import com.youliao.sdk.news.provider.AnalyticsProvider

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val currentProcessName = getCurrentProcessName()
        if (packageName == currentProcessName) {
            YouliaoNewsSdk.apply {
                // 此方法不会请求网络，请放在Application中调用
                init(
                    this@MyApplication,
                    "8a6ac39f33388ae6",
                    "b1a571291720867c7e9bf6d2c718dc7e",
                    "Test"
                )
                setShareAppId("1107990332", "wx8b0b139d1103eaa0")
                // 可以依赖'com.youliao.sdk:msa:1.1.5'，或者自行实现OaidProvider接口
//            setOaidProvider(MasOaidProvider(this@MyApplication))
                // 可以依赖'com.youliao.sdk:amaplocation:1.1.5'，或者自行实现LocationProvider接口
                setLocationProvider(AMapLocationProvider(this@MyApplication))

                setAnalyticsProvider(object : AnalyticsProvider {
                    override fun onEventObject(
                        eventId: String,
                        map: Map<String, Any>,
                        api: Boolean
                    ) {
                    }
                })
                // 此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
                requestSdkConfig()
                // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
//            initAdroi("a8b3a9047", "有料看看_android")
                // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
                initBytedanceAd("5011189", "有料看看_测试_android")
                // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
                initBytedanceDp("appId", "secureKey", "partner", "appLogId")
            }

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun getCurrentProcessName(): String? {
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
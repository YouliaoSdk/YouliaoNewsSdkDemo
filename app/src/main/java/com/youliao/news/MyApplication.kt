package com.youliao.news

import android.os.Build
import android.webkit.WebView
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.youliao.news.utils.Utils
import com.youliao.sdk.location.AMapLocationProvider
import com.youliao.sdk.news.YouliaoNewsSdk
import com.youliao.sdk.news.provider.AnalyticsProvider

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        val currentProcessName = Utils.getCurrentProcessName(this)
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
                // 可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
//            setOaidProvider(MasOaidProvider(this@MyApplication))
                // 可以依赖'com.youliao.sdk:amaplocation:1.2.0'，或者自行实现LocationProvider接口
                setLocationProvider(AMapLocationProvider(this@MyApplication))

                setAnalyticsProvider(object : AnalyticsProvider {
                    override fun onEventObject(
                        eventId: String,
                        map: Map<String, Any>,
                        api: Boolean
                    ) {
                    }
                })
                // 此方法会请求网络，为了合规请在用户同意协议后调用
                requestSdkConfig()
                // 此方法用于获取用户所在城市，为了合规请在用户同意协议后调用
                requestLocation()

                // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
//            initAdroi("a8b3a9047", "有料看看_android")
                // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
                // （不包括通过adroi接入，因为adroi是动态获取appid，所有可能会不初始化或初始化较晚造成头条小视频sdk加载失败或崩溃）
                // 为了合规请在用户同意协议后调用
                initBytedanceAd("5011189", "有料看看_测试_android")
                // 此方法用于初始化头条小视频sdk，为了合规请在用户同意协议后调用
                initBytedanceDp("SDK_Setting_xxxxxxx.json", false)
                // 此方法用于初始化头条小说sdk，为了合规请在用户同意协议后调用
//                initBytedanceNovel("appName", "SDK_Setting_xxxxxxx.json", false)
            }

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}
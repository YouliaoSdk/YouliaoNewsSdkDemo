package com.youliao.news.java;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.youliao.sdk.location.AMapLocationProvider;
import com.youliao.sdk.news.YouliaoNewsSdk;

public class JavaMyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 此方法不会请求网络，请放在Application中调用
        YouliaoNewsSdk.init(this, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab")
                .setShareAppId("1107937097", "wx83f749fd20846f7f")
                // 可以依赖'com.youliao.sdk:msa:1.1.5'，或者自行实现OaidProvider接口
//                .setOaidProvider(new MasOaidProvider(this))
                // 可以依赖'com.youliao.sdk:amaplocation:1.1.5'，或者自行实现LocationProvider接口
                .setLocationProvider(new AMapLocationProvider(this));

        // 此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
        YouliaoNewsSdk.requestSdkConfig();
        // 此方法用于获取用户所在城市
        YouliaoNewsSdk.requestLocation();

        // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
//        YouliaoNewsSdk.initAdroi("a8b3a9047", "ADroi广告demo")
        // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
        YouliaoNewsSdk.initBytedanceAd("5011189", "有料看看_测试_android");
        // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
        YouliaoNewsSdk.initBytedanceDp("appId", "secureKey", "partner", "appLogId");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

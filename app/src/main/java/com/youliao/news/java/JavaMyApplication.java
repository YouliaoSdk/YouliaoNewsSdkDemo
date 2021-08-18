package com.youliao.news.java;

import androidx.multidex.MultiDexApplication;

import com.youliao.news.utils.Utils;
import com.youliao.sdk.location.AMapLocationProvider;
import com.youliao.sdk.news.YouliaoNewsSdk;

public class JavaMyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        String currentProcessName = Utils.getCurrentProcessName(this);
        if (getPackageName().equals(currentProcessName)) {
            // 此方法不会请求网络，请放在Application中调用
            YouliaoNewsSdk.init(this, "6346e1a6f5fc82ed", "5c16f8d71854b47601d3a31c87b0e0ab")
                    .setShareAppId("1107937097", "wx83f749fd20846f7f")
                    // 可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
//                .setOaidProvider(new MasOaidProvider(this))
                    // 可以依赖'com.youliao.sdk:amaplocation:1.2.0'，或者自行实现LocationProvider接口
                    .setLocationProvider(new AMapLocationProvider(this));

            // 此方法会请求网络，为了合规请在用户同意协议后调用
            YouliaoNewsSdk.requestSdkConfig();
            // 此方法用于获取用户所在城市，为了合规请在用户同意协议后调用
            YouliaoNewsSdk.requestLocation();

            // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
            // 为了合规请在用户同意协议后调用
//        YouliaoNewsSdk.initAdroi("a8b3a9047", "ADroi广告demo")
            // 此方法用于初始化穿山甲sdk，如果已经接入过穿山甲sdk或不需要穿山甲广告，请忽略
            // （不包括通过adroi接入，因为adroi是动态获取appid，所有可能会不初始化或初始化较晚造成头条小视频sdk加载失败或崩溃）
            // 为了合规请在用户同意协议后调用
            YouliaoNewsSdk.initBytedanceAd("5011189", "有料看看_测试_android");
            // 此方法用于初始化头条小视频sdk，为了合规请在用户同意协议后调用
            YouliaoNewsSdk.initBytedanceDp("SDK_Setting_xxxxxxx.json", false);
            // 此方法用于初始化头条小说sdk，为了合规请在用户同意协议后调用
//            YouliaoNewsSdk.initBytedanceNovel(
//                    "appId",
//                    "appName",
//                    "preAdCodeId",
//                    "midAdCodeId",
//                    "excitingAdCodeId",
//                    "bannerAdCodeId",
//                    ""
//            );
        }
    }
}

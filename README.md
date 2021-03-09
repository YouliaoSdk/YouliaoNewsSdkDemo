# 有料信息流sdk接入文档

|  版本号 | 日期 | 说明 |
| ---- | ---- | --- |
| 1.1.5-rc01 | 2021-2-7 | 创建文档，支持穿山甲小说sdk |
| 1.1.6-rc01 | 2021-3-4 | 支持穿山甲小说单频道，支持快手小视频|

## CHANGELOG
- [CHANGELOG.md](./CHANGELOG.md)

## SDK接入前说明

新用户需要联系商务申请必要的参数，目前需要的此参数有以下：

1.Appid

2.Apikey

请务必保存好这2个参数，这些是sdk初始化必须的参数，否则sdk不能正常运行。

## SDK接入
### 一、增加依赖

1. 在主project的`allprojects` -> `repositories`添加
   ```groovy
   allprojects {
       repositories {
           google()
           jcenter()
         	 // 增加下面一行
           maven { url 'https://api.youliaokk.com:21380/repository/qj-feeds-sdk/' }
       }
   }
   ```

2. 在app工程的`dependencies`添加
   ```groovy
   dependencies {
       // 增加下面依赖
       implementation 'com.youliao.sdk:news:1.1.6-rc01'
       // 如果使用glide4.x，增加依赖
       implementation 'com.youliao.sdk:glide4:1.1.5'
   }
   ```

3. 接入`adroi sdk`，并且之前没有接入过`adroi sdk`，请按照`adroi sdk`文档进行接入
**注意**
1.1.6-rc01版本对应的adroi sdk版本为`3.7.7`，请尽量保持一致，以免有兼容性问题

4. 接入`头条内容合作sdk`：

    1） 添加sdk，可以在sdk目录下载`dpsdk_2.4.0.2.aar`

        implementation(name: 'dpsdk_2.4.0.2', ext: 'aar')
        // 新增依赖，穿山甲小说sdk 需要相同依赖。如果两个sdk都接入只需要添加一次
        implementation ("com.volcengine:apm_insight_crash:1.3.7") {
            exclude group: 'com.android.support'
            exclude group: 'com.bytedance.applog'
        }
        // 如果使用androidx，请添加下面一行
        implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    2）需要接入穿山甲sdk，请参照adroi文档进行接入

    3）如果之前有添加过`AppLog`库，或已接入内容合作sdk（该sdk依赖AppLog），需要`替换`AppLog aar包为：
 
        implementation "com.bytedance.applog:RangersAppLog-Lite-cn:5.4.1-rc.0-utility"
    
    3）如果`AndroidMenifest.xml`中添加：

        <provider
            android:name="com.bytedance.sdk.dp.act.DPProvider"
            android:authorities="${applicationId}.BDDPProvider"
            android:exported="false" />

5. 接入`穿山甲小说sdk`：

    1）添加sdk，可以在sdk目录下载`open_novel_sdk_1.0.6.aar`
  
        implementation(name: 'open_novel_sdk_1.0.6', ext: 'aar')
        // 新增依赖，头条内容合作sdk 需要相同依赖。如果两个sdk都接入只需要添加一次
        implementation ("com.volcengine:apm_insight_crash:1.3.7") {
            exclude group: 'com.android.support'
            exclude group: 'com.bytedance.applog'
        }
       
    2）需要接入穿山甲sdk，请参照adroi文档进行接入

    3）如果之前有添加过`AppLog`库，或已接入内容合作sdk（该sdk依赖AppLog），需要`替换`AppLog aar包为：
 
        implementation "com.bytedance.applog:RangersAppLog-Lite-cn:5.4.1-rc.0-utility"
      
    4）在`YouliaoNewsSdk.init(this, "appid", "apikey", "channel")`方法下面添加：
  
        YouliaoNewsSdk.initBytedanceNovel(appid, "应用名称") // appid有料这边会提供，该方法不会有网络请求，可以在application中调用

    5）获取小说单频道fragment

        YouliaoNewsSdk.getBytedanceNovelFragment()

    6）如果使用`AndroidX`需要添加以下到`AndroidMenifest.xml`
  
        <activity
            android:name="com.bytedance.novel.view.NovelReaderActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            tools:replace="android:theme">
            <meta-data
                android:name="android.notch_support"
                android:value="true" />
        </activity>

        <activity
            android:name="com.bytedance.novel.channel.NovelWebActivity"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            tools:replace="android:theme">
            <meta-data
                android:name="android.notch_support"
                android:value="true" />
        </activity>

    7）由于小说sdk aar只提供了`armeabi-v7a`、`arm64-v8a`的so库，所以需要根据自身需要设置正确的`abiFilters`

6. 接入`快手小视频sdk`：

    1）添加sdk，可以在sdk目录下载`kssdk-all-3.3.11.aar`，如果之前有接入快手广告sdk需要`删除`原有aar包
        
        implementation(name: 'kssdk-all-3.3.11', ext: 'aar')
    
    2）在`YouliaoNewsSdk.init(this, "appid", "apikey", "channel")`方法下面添加：

        YouliaoNewsSdk.initKs(appid, "应用名称") // appid有料这边会提供，该方法不会有网络请求，可以在application中调用
    
    3）如果使用androidX，请确保以下两个依赖不能大于1.2.5

        implementation 'androidx.fragment:fragment:1.2.5'
        implementation 'androidx.fragment:fragment-ktx:1.2.5'

### 二、初始化及基本配置

1. 在Application中的`onCreate`添加

   ```java
   // java
   // *重要*,请添加下面这行
   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
   // sdk方法
   // 此方法不会请求网络，请放在Application中调用，appid和secret参数会由渠杰提供，channel由接入方填入
   YouliaoNewsSdk.init(this, "appid", "apikey", "channel")
   	.setShareAppId("qqappid", "wxappid"); // qqappid，wxappid
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.1.5'，或者自行实现OaidProvider接口
    .setOaidProvider(new MasOaidProvider(this))
    // 可以依赖'com.youliao.sdk:amaplocation:1.1.5'，或者自行实现LocationProvider接口
    // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
    .setLocationProvider(new AMapLocationProvider(this));
   
   // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
   YouliaoNewsSdk.requestSdkConfig();
   // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用。
   // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
   YouliaoNewsSdk.requestLocation();
   
   // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
   YouliaoNewsSdk.initAdroi("adroi-appid", "ADroi广告demo")

    // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
    YouliaoNewsSdk.initBytedanceDp("appId", "secureKey", "partner", "appLogId");
   ```

   ```kotlin
   // kotlin
   // *重要*,请添加下面这行
   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
   // sdk方法
   YouliaoNewsSdk.apply {
     // 此方法不会请求网络，请放在Application中调用，appid和secret参数会由渠杰提供，channel由接入方填入
     init(this@MyApplication, "appid", "apikey", "channel")
     setShareAppId("qqappid","wxappid")
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.1.5'，或者自行实现OaidProvider接口
     setOaidProvider(MasOaidProvider(this@MyApplication))
     // 可以依赖'com.youliao.sdk:amaplocation:1.1.5'，或者自行实现LocationProvider接口，或者不需要本地频道
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     setLocationProvider(AMapLocationProvider(this@MyApplication))
     // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
     requestSdkConfig()
     // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     requestLocation()
     
     // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略
     initAdroi("adroi-appid", "ADroi广告demo")

     // 此方法用于初始化穿山甲小视频sdk，如果接入穿山甲小视频sdk必须调用此方法，参数有料会提供
     initBytedanceDp("appId", "secureKey", "partner", "appLogId")
   }
   ```

### 三、使用

1. 创建NewsFragment并显示

   ```java
   // java
   FragmentManager fragmentManager = getSupportFragmentManager();
   FragmentTransaction transaction = fragmentManager.beginTransaction();
   fragment = NewsFragment.newInstance("news", false, "上海");
   // 第一次参数是 tab类型，默认为news，只有一个信息流页面时可以不设置
   // 第二个参数是 是否显示右下角的刷新按钮，默认false
   // 第三个参数是 城市名称，当在这里传入城市时，不要再调用requestLocation也不要调用setLocationProvider，正常情况下不要填写这个参数
   transaction.replace(R.id.container, fragment);
   transaction.commit();
   ```

   ```kotlin
   // kotlin
   val transaction = supportFragmentManager.beginTransaction()
   val fragment = NewsFragment.newInstance("news", false, "上海")
   // 第一次参数是 tab类型，默认为news，只有一个信息流页面时可以不设置
   // 第二个参数是 是否显示右下角的刷新按钮，默认false
   // 第三个参数是 城市名称，当在这里传入城市时，不要再调用requestLocation也不要调用setLocationProvider，正常情况下不要填写这个参数
   transaction.replace(R.id.container, fragment)
   transaction.commit()
   ```

2. 设置主题色
  在`res`->`values`->`colors.xml`中覆盖以下字段
  ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <resources>
      <!--recyclerview顶部、底部提示背景色，负反馈文字边框颜色 等-->
      <color name="youliao_primary">#6FCCFF</color>
      <!--频道tab选中颜色，按钮颜色，详情页progressbar颜色等-->
      <color name="youliao_primary_dark">#005DFF</color>
   </resources>
  ```

3. 更换loading图
   在`res`->`mipmap`下覆盖`youliao_loading_logo.png`文件
   `注意`,每个dpi文件夹下都要覆盖

4. 更换部分尺寸
  在`res`->`values`->`dimens.xml`中覆盖以下字段
  ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <resources>
    <!--顶部频道文字size-->
    <dimen name="youliao_tab_title">17sp</dimen>
    <!--新闻标题文字size-->
    <dimen name="youliao_news_title">18sp</dimen>
    <!--新闻左右边距-->
    <dimen name="youliao_news_margin_horizontal">15dp</dimen>
   </resources>
  ```

5. NewsFragment实例方法

   ```kotlin
   // kotlin
   // 1：是否已经滑动到顶部（部分页面可能不支持）
   fun isScrollTop(): Boolean
   // 2：刷新新闻，第一个参数表示是否跳转到第一个tab（部分页面可能不支持）
   fun refreshData(firstTab: Boolean = false)
   // 3：滑动到顶部，第一个参数表示是否平滑的滑动到顶部（部分页面可能不支持）
   fun scrollToTop(smooth: Boolean = false)
   ```

### 四、其他
1. 关于混淆
    混淆规则已经打在aar包里


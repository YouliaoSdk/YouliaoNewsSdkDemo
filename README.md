# 有料信息流sdk接入文档

|  版本号 | 日期 | 说明 |
| ---- | ---- | --- |
| 1.1.5-rc01 | 2021-2-7 | 创建文档，支持穿山甲小说sdk |
| 1.1.6-rc01 | 2021-3-4 | 支持穿山甲小说单频道，支持快手小视频 |
| 1.1.8-rc06 | 2021-4-16 | 增加统计，修复bug |
| 1.1.9-rc01 | 2021-5-13 | 适配 adroi-sdk:3.8.7，头条内容sdk:2.4.1.0，小说sdk:2.0.2 |
| 1.2.0-rc02 | 2021-5-13 | 迁移到androidx，适配 adroi-sdk:3.8.7，头条内容sdk:2.4.1.0，小说sdk:2.0.2 |
| 1.2.1-rc03 | 2021-6-17 | 适配 adroi-sdk:3.9.7/3.9.9.3，头条内容sdk:2.7.1.2，小说sdk:3.0.1 |
| 1.2.2-rc01 | 2021-8-17 | 适配 adroi-sdk:10.0.0.1，头条内容sdk和小说合并 sdk版本号: 1.0.0.0 |
| 1.2.3-rc01 | 2021-9-6 | 适配 adroi-sdk:10.0.0.3，头条内容sdk和小说合并 sdk版本号: 1.2.0.0 |

## CHANGELOG
- [CHANGELOG.md](./CHANGELOG.md)

## Demo说明
由于各第三方sdk都需要验证包名以及签名，demo运行可能会有问题，demo只提供用于接入参考

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
        implementation 'com.youliao.sdk:news:1.2.3-rc01'
        // 如果使用glide4.x，增加依赖
        implementation 'com.youliao.sdk:glide4:1.2.0'
        // 如果使用coil，增加依赖
        implementation 'com.youliao.sdk:coil:1.2.0'
   }
   ```

3. 接入`adroi sdk`，并且之前没有接入过`adroi sdk`，请按照`adroi sdk`文档进行接入
**注意**
`1.2.3-rc01`版本对应的adroi sdk版本为`10.0.0.3`，请尽量保持一致，以免有兼容性问题

4. 接入`头条短视频sdk`：

    1） 添加sdk
    
        // 在allprojects的repositories中添加，如果需要同时接入小说，只需要添加一次
        maven { url "https://artifact.bytedance.com/repository/pangle/" }
        maven { url "https://artifact.bytedance.com/repository/Volcengine/" }

        // 穿山甲广告Sdk，可以使用在线依赖的方式，也可以使用adroi提供的aar包
        implementation 'com.pangle.cn:ads-sdk:3.9.0.2'
        implementation ('com.pangle.cn:pangrowth-sdk:1.2.0.0'){
            exclude group: 'com.pangle.cn', module: 'pangrowth-dpsdk-live'
            exclude group: 'com.pangle.cn', module: 'pangrowth-novel-sdk' // 如果需要同时接入小说，需要删除本行
            exclude group: 'com.pangle.cn', module: 'pangrowth-game-sdk'
            exclude group: 'com.pangle.cn', module: 'pangrowth-luckycat-sdk'
        }

    2）需要接入穿山甲sdk，请参照adroi文档进行接入

    3）初始化，为了合规请在用户同意协议之后调用：

        initBytedanceDp("配置json文件名", false) // 该配置文件请从穿山甲后台下载，并放到assets目录下

    4）在app的build.gradle中添加

        android{
            defaultConfig{
                manifestPlaceholders.put("APPLOG_SCHEME", "rangersapplog.xxxxxxxx".toLowerCase())
            }
        }
    
    5）如果`AndroidMenifest.xml`中添加：

        <provider
            android:name="com.bytedance.sdk.dp.act.DPProvider"
            android:authorities="${applicationId}.BDDPProvider"
            android:exported="false" />

5. 接入`头条小说sdk`：

    1）添加sdk
  
        // 在allprojects的repositories中添加，如果需要同时接入短视频（含图文），只需要添加一次
        maven { url "https://artifact.bytedance.com/repository/pangle/" }
        maven { url "https://artifact.bytedance.com/repository/Volcengine/" }

        // 穿山甲广告Sdk，可以使用在线依赖的方式，也可以使用adroi提供的aar包
        implementation 'com.pangle.cn:ads-sdk:3.9.0.2'
        implementation ('com.pangle.cn:pangrowth-sdk:1.2.0.0'){
            exclude group: 'com.pangle.cn', module: 'pangrowth-dpsdk-live'
            exclude group: 'com.pangle.cn', module: 'pangrowth-dpsdk' // 如果需要同时接入短视频（含图文），需要删除本行
            exclude group: 'com.pangle.cn', module: 'pangrowth-game-sdk'
            exclude group: 'com.pangle.cn', module: 'pangrowth-luckycat-sdk'
        }
       
    2）需要接入穿山甲sdk，请参照adroi文档进行接入
      
    3）初始化，为了合规请在用户同意协议之后调用：
  
        YouliaoNewsSdk.initBytedanceNovel("应用名称", "配置json文件名", false) // 该配置文件请从穿山甲后台下载，并放到assets目录下

    4）获取小说单频道fragment

        YouliaoNewsSdk.getBytedanceNovelFragment()

    5）添加以下内容到`AndroidMenifest.xml`
  
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

6. 接入`快手小视频sdk`：

    1）添加sdk，可以在sdk目录下载`kssdk-all-3.3.19.1.aar`，如果之前有接入快手广告sdk需要`删除`原有aar包
        
        implementation(name: 'kssdk-all-3.3.19.1', ext: 'aar')
        // 如果使用androidx，需要添加此依赖
        implementation 'androidx.legacy:legacy-support-core-ui:1.0.0'
    
    2）在`YouliaoNewsSdk.init(this, "appid", "apikey", "channel")`方法下面添加：

        YouliaoNewsSdk.initKs(appid, "应用名称") // appid有料这边会提供
    
    3）请确保以下两个依赖不能大于1.2.5，或者查看第四点

        implementation 'androidx.fragment:fragment:1.2.5'
        implementation 'androidx.fragment:fragment-ktx:1.2.5'

        如果需要使用fragmnet:1.2.5以上的版本，则需要调用以下代码：
            FragmentManager.enableNewStateManager(false)

    4) 如果不能满足第三点，则需要调用下面的方法

        FragmentManager.enableNewStateManager(false)

    5) 新增快手合规开关
        YouliaoNewsSdk.updateKsRecommendation(true) // 默认true。true:推荐 false:合规

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
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
    .setOaidProvider(new MasOaidProvider(this))
    // 可以依赖'com.youliao.sdk:amaplocation:1.2.0'，或者自行实现LocationProvider接口
    // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
    .setLocationProvider(new AMapLocationProvider(this));
   
   // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
   YouliaoNewsSdk.requestSdkConfig();
   // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用。
   // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
   YouliaoNewsSdk.requestLocation();
   
   // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略；为了合规请在用户同意协议之后调用
   YouliaoNewsSdk.initAdroi("adroi-appid", "ADroi广告demo", false);
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
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
     setOaidProvider(MasOaidProvider(this@MyApplication))
     // 可以依赖'com.youliao.sdk:amaplocation:1.2.0'，或者自行实现LocationProvider接口，或者不需要本地频道
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     setLocationProvider(AMapLocationProvider(this@MyApplication))
     // 注意：此方法会请求网络，如果有流量提醒弹框，可以在用户点击确认后再调用。不一定放在application中
     requestSdkConfig()
     // 注意：此方法用于获取用户所在城市，请在获取定位权限后调用
     // 如果在NewsFragment.newInstance中有传入city，请不要再调用该方法
     requestLocation()
     
     // 此方法用于初始化adroi sdk，如果已经接入过adroi sdk或不需要adroi广告，请忽略；为了合规请在用户同意协议之后调用
     initAdroi("adroi-appid", "ADroi广告demo")
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


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
| 1.2.4-rc01 | 2021-9-23 | 适配 adroi-sdk:10.0.0.3，头条内容sdk和小说合并 sdk版本号: 1.3.0.0 |
| 1.2.5-rc02 | 2022-2-18 | 适配 adroi-sdk:10.0.0.17，头条内容合作-sdk版本号: 1.7.0.0 |
| 1.2.6-rc01 | 2022-3-28 | 适配 adroi-sdk:10.0.0.23，头条内容合作-sdk版本号: 2.2.0.0 |
| 1.2.7-beta03 | 2022-4-28 | 适配 adroi-sdk:10.0.0.29.2，头条内容合作-sdk版本号: 2.4.0.0 |
| 1.2.8-beta01 | 2022-5-28 | 适配 adroi-sdk:10.0.0.33，头条内容合作-sdk版本号: 2.5.0.0 |
| 1.2.9-beta03 | 2022-7-21 | 适配 adroi-sdk:10.0.0.39，头条内容合作-sdk版本号: 2.5.0.0 |
| 1.3.0-beta01 | 2021-8-24 | 适配 adroi-sdk:10.0.0.45<br>头条内容合作-sdk版本号: 2.7.0.6<br>穿山甲广告-sdk版本号：4.6.0.7<br>快手-sdk版本号：3.3.29<br>百度内容-sdk版本号：9.22<br>百度小说-sdk版本号：6.0.3.5 |
| 1.3.0-beta03 | 2022-9-2 | 适配 adroi-sdk:10.0.0.51<br>头条内容合作-sdk版本号: 2.7.0.6<br>穿山甲广告-sdk版本号：4.7.1.2<br>快手-sdk版本号：3.3.31<br>百度内容-sdk版本号：9.23<br>百度小说-sdk版本号：6.0.3.5 |
| 1.3.2-beta06 | 2022-10-8 | 适配 adroi-sdk:10.0.0.51<br>头条内容合作-sdk版本号: 2.7.0.6<br>穿山甲广告-sdk版本号：4.7.1.2<br>快手-sdk版本号：3.3.31<br>百度内容-sdk版本号：9.23<br>百度小说-sdk版本号：6.0.3.5 |
| 1.3.3-beta01 | 2022-11-18 | 新增百度小说书架列表api<br>新增番茄小说书架列表api<br>信息流item去除默认背景<br>编辑频道icon适配深色模式<br>优化每个频道首次请求成功后，不展示成功提示 |
| 1.3.6-beta04 | 2023-1-16 | 适配 adroi-sdk:10.0.0.63<br>头条内容合作-sdk版本号: 2.9.0.4<br>快手内容-sdk版本号：3.3.32<br>百度小说-sdk版本号：6.0.3.6<br>百度广告-sdk版本号：9.25<br>gromore-sdk版本号：3.8.0.2<br>gradle升级至7.4<br>百度小说适配夜间模式<br>新增百度内容联盟热点专题<br>修复百度小说封面可能为null的问题<br>移除头条小说 |
| 1.4.2-beta04 | 2023-6-12 | 适配 gromore:3.9.0.0<br>接入搜狐api<br>开启 gromore bidding 比价结果通知 |

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
           // 如需接入 快手小视频sdk 或者 百度小说sdk ，增加下面一行
           // maven { url 'https://maven.freemeos.com:13458/repository/adroi/' }
       }
   }
   ```

2. 在app工程的`dependencies`添加
   ```groovy
   dependencies {
        // 增加下面依赖
        implementation 'com.youliao.sdk:news:1.4.2-beta04'
        // 如果使用glide4.x，增加依赖
        implementation 'com.youliao.sdk:glide4:1.3.0-rc01'
        // 如果使用coil，增加依赖
        implementation 'com.youliao.sdk:coil:1.3.0-rc01'
   }
   ```

3. 接入`adroi sdk`，并且之前没有接入过`adroi sdk`，请按照`adroi sdk`文档进行接入
**注意**
当前最新版本对应的adroi sdk版本为`10.0.0.63`，请尽量保持一致，以免有兼容性问题

4. 接入`头条短视频sdk`：

    1） 添加sdk

        // 在allprojects的repositories中添加，如果需要同时接入小说，只需要添加一次
        maven { url "https://artifact.bytedance.com/repository/pangle/" }
        maven { url "https://artifact.bytedance.com/repository/Volcengine/" }
        maven { url 'https://artifact.bytedance.com/repository/AwemeOpenSDK' }

        // 穿山甲内容Sdk，可以使用在线依赖的方式，也可以使用adroi提供的aar包
        implementation ('com.pangle.cn:pangrowth-sdk:2.9.0.4'){
            exclude group: 'com.pangle.cn', module: 'pangrowth-game-sdk'
            exclude group: 'com.pangle.cn', module: 'pangrowth-luckycat-sdk'
            exclude group: 'com.pangle.cn', module: 'partner-luckycat-api-sdk'
            exclude group: 'com.pangle.cn', module: 'pangrowth-reward-sdk'
            exclude group: 'com.pangle.cn', module: 'partner-live-sdk'
        }

    2）需要接入穿山甲sdk，请参照adroi文档进行接入

    3）初始化，为了合规请在用户同意协议之后调用：

        initBytedanceDp("配置json文件名") // 该配置文件请从穿山甲后台下载，并放到assets目录下

    4）在app的build.gradle中添加
   
        //小视频3100及以上版本必须依赖gradle脚本，否则sdk不能正常运行
        apply from: 'https://sf3-fe-tos.pglstatp-toutiao.com/obj/pangle-empower/android/pangrowth_media/plugin_config.gradle'

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

6. 接入`快手小视频sdk`：

    1）添加sdk，如果之前有接入快手广告sdk需要`删除`原有aar包
        
        implementation 'com.kssdk.sdk:kssdk-ct:3.3.32'
        // 如果使用androidx，需要添加此依赖
        implementation 'androidx.legacy:legacy-support-core-ui:1.0.0'

    2）初始化，为了合规请在用户同意协议之后调用：

        YouliaoNewsSdk.initKs(appid, "应用名称") // appid有料这边会提供

    3) 需要调用下面的方法

        FragmentManager.enableNewStateManager(false)

    4) 新增快手合规开关
        YouliaoNewsSdk.updateKsRecommendation(false) // 默认true。true:推荐 false:合规

7. 接入`百度小说sdk`：

   1）添加sdk

        //注意小说版本需要和百度内容版本匹配
        implementation 'com.baidu:novel.sdk:6.0.3.6'

   2）需要接入百度内容sdk，请参照adroi文档进行接入

   3）初始化，为了合规请在用户同意协议之后调用：

        YouliaoNewsSdk.initBaiduNovel(appsid, "应用名称") // appid有料这边会提供

   4）获取小说单频道fragment

        YouliaoNewsSdk.getBaiduNovelFragment()

   
### 二、初始化及基本配置

1. 在Application中的`onCreate`添加

   ```java
   // java
   // *重要*,请添加下面这行
   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
   // sdk方法
   // 此方法不会请求网络，请放在Application中调用，appid和secret参数会由渠杰提供，channel由接入方填入，json配置文件渠杰运营会提供，并放到assets目录下
   YouliaoNewsSdk.init(this, "appid", "apikey", "channel", "配置json文件名")
   	.setShareAppId("qqappid", "wxappid"); // qqappid，wxappid
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
    .setOaidProvider(new MasOaidProvider(this))

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
     // 此方法不会请求网络，请放在Application中调用，appid和secret参数会由渠杰提供，channel由接入方填入，json配置文件渠杰运营会提供，并放到assets目录下
     init(this@MyApplication, "appid", "apikey", "channel", "配置json文件名")
     setShareAppId("qqappid","wxappid")
    // 如果接入oaid，并且oaid版本为：1.0.25，可以依赖'com.youliao.sdk:msa:1.2.0'，或者自行实现OaidProvider接口
     setOaidProvider(MasOaidProvider(this@MyApplication))
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
   // 1：刷新新闻，第一个参数表示是否跳转到第一个tab（部分页面可能不支持）
   fun refreshData(firstTab: Boolean = false)
   // 2：滑动到顶部，第一个参数表示是否平滑的滑动到顶部（部分页面可能不支持）
   fun scrollToTop(smooth: Boolean = false)
   //3.设置全局字体大小
   YouliaoNewsSdk.setNewsDetailFontSize(fontSize: FontSize)
   //4.设置新闻详情页字体大小
   YouliaoNewsSdk.setNewsDetailFontSize(fontSize: FontSize)
   ```

### 四、其他
1. 关于混淆
    混淆规则已经打在aar包里


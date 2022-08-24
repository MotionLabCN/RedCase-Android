package com.tntlinking.tntdev.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tntlinking.tntdev.R;
import com.hjq.gson.factory.GsonFactory;
import com.hjq.http.EasyConfig;
import com.hjq.toast.ToastUtils;
import com.hjq.umeng.UmengClient;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.tntlinking.tntdev.aop.Log;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.RequestHandler;
import com.tntlinking.tntdev.http.model.RequestServer;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.CrashHandler;
import com.tntlinking.tntdev.other.DebugLoggerTree;
import com.tntlinking.tntdev.other.TitleBarStyle;
import com.tntlinking.tntdev.other.ToastLogInterceptor;
import com.tntlinking.tntdev.other.ToastStyle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * desc   : 应用入口
 */
public final class AppApplication extends Application {
    @Log("启动耗时")
    @Override
    public void onCreate() {
        super.onCreate();

        initSdk(this);
        initToast();
        initKV();

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // 清理所有图片内存缓存
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 根据手机内存剩余情况清理图片内存缓存
        GlideApp.get(this).onTrimMemory(level);
    }

    private void initKV() {
        MMKV.initialize(this);
    }

    private void initToast() {
        ToastUtils.init(this);
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSdk(Application application) {
        // 设置标题栏初始化器
        TitleBar.setDefaultStyle(new TitleBarStyle());

//        // 设置全局的 Header 构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator((cx, layout) -> new MaterialHeader(application).setColorSchemeColors(ContextCompat.getColor(application, R.color.common_accent_color)));
//        // 设置全局的 Footer 构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator((cx, layout) -> new SmartBallPulseFooter(application));
//        // 设置全局初始化器
//        SmartRefreshLayout.setDefaultRefreshInitializer((cx, layout) -> {
//            // 刷新头部是否跟随内容偏移
//            layout.setEnableHeaderTranslationContent(true)
//                    // 刷新尾部是否跟随内容偏移
//                    .setEnableFooterTranslationContent(true)
//                    // 加载更多是否跟随内容偏移
//                    .setEnableFooterFollowWhenNoMoreData(true)
//                    // 内容不满一页时是否可以上拉加载更多
//                    .setEnableLoadMoreWhenContentNotFull(false)
//                    // 仿苹果越界效果开关
//                    .setEnableOverScrollDrag(false);
//        });

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.background_dark);//全局设置主题颜色

                return new ClassicsHeader(context).setEnableLastTime(false);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });

        // 初始化吐司
        ToastUtils.init(application, new ToastStyle());
        // 设置调试模式
        ToastUtils.setDebugMode(AppConfig.isDebug());
        // 设置 Toast 拦截器
        ToastUtils.setInterceptor(new ToastLogInterceptor());
        // 本地异常捕捉
        CrashHandler.register(application);

//        // 友盟统计、登录、分享 SDK
//        UmengClient.init(application, AppConfig.isLogEnable());
//        // 初始化极光推送
//        JPushInterface.setDebugMode(AppConfig.isDebug());
//        JPushInterface.init(application);
//        HeytapPushManager.init(application,AppConfig.isDebug());
        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, AppConfig.getBuglyId(), AppConfig.isDebug());

        // Activity 栈管理初始化
        ActivityManager.getInstance().init(application);

        // MMKV 初始化
        MMKV.initialize(application);

        // 网络请求框架初始化
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(AppConfig.isLogEnable())
                // 设置服务器配置
                .setServer(new RequestServer())
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                .setInterceptor((api, params, headers) -> {
                    // 添加全局请求头
                    headers.put("deviceOaid", UmengClient.getDeviceOaid());
                    headers.put("versionName", AppConfig.getVersionName());
                    headers.put("versionCode", String.valueOf(AppConfig.getVersionCode()));
                    if (!TextUtils.isEmpty(SPUtils.getInstance().getString(AppConfig.ACCESS_TOKEN))) {
                        headers.put("Authorization", SPUtils.getInstance().getString(AppConfig.ACCESS_TOKEN));
                    }
                    // 添加全局请求参数
                    // params.put("6666666", "6666666");
                })
                .into();

        // 设置 Json 解析容错监听
        GsonFactory.setJsonCallback((typeToken, fieldName, jsonToken) -> {
            // 上报到 Bugly 错误列表
            CrashReport.postCatchedException(new IllegalArgumentException(
                    "类型解析异常：" + typeToken + "#" + fieldName + "，后台返回的类型为：" + jsonToken));
        });

        // 初始化日志打印
        if (AppConfig.isLogEnable()) {
            Timber.plant(new DebugLoggerTree());
        }

        // 注册网络状态变化监听
        ConnectivityManager connectivityManager = ContextCompat.getSystemService(application, ConnectivityManager.class);
        if (connectivityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onLost(@NonNull Network network) {
                    Activity topActivity = ActivityManager.getInstance().getTopActivity();
                    if (!(topActivity instanceof LifecycleOwner)) {
                        return;
                    }

                    LifecycleOwner lifecycleOwner = ((LifecycleOwner) topActivity);
                    if (lifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                        return;
                    }

                    ToastUtils.show(R.string.common_network_error);
                }
            });
        }

        boolean isPrivacyAccepted = SPUtils.getInstance().getBoolean(AppConfig.DEAL_DIALOG, false);
        if (isPrivacyAccepted) {
            // 初始化极光推送
            JPushInterface.setDebugMode(AppConfig.isDebug());
            JPushInterface.init(application);
            // 友盟统计、登录、分享 SDK
            UmengClient.init(application, AppConfig.isLogEnable());
        } else {

        }

    }
}
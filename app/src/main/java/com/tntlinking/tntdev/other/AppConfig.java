package com.tntlinking.tntdev.other;

import com.tntlinking.tntdev.BuildConfig;

/**
 * desc   : App 配置管理类
 */
public final class AppConfig {

    /**
     * 当前是否为调试模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前构建的模式
     */
    public static String getBuildType() {
        return BuildConfig.BUILD_TYPE;
    }

    /**
     * 当前是否要开启日志打印功能
     */
    public static boolean isLogEnable() {
        return BuildConfig.LOG_ENABLE;
    }

    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取 Bugly Id
     */
    public static String getBuglyId() {
        return BuildConfig.BUGLY_ID;
    }

    /**
     * 获取服务器主机地址
     */
    public static String getHostUrl() {
        return BuildConfig.HOST_URL;
    }

    /**
     * access_token
     */
    public static String ACCESS_TOKEN = "access_token";

    /**
     *
     */
    private static String DEVELOPER_ID = "developer_id";
    /**
     * 是否登陆
     */
    public static String HAS_LOGIN = "has_login";

    /**
     * 是否认证  1->待认证  2->待审核   3->审核成功 4->审核失败
     */
    public static String DEVELOP_STATUS = "develop_status";

    public static String MOBILE = "mobile";

    public static String DEVELOP_NAME = "develop_name";
}
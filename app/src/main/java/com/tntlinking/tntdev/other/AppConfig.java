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
        return BuildConfig.DEBUG;
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
        return HOST_URL;
    }

    /**
     * 请求地址
     */
    public static String HOST_URL = BuildConfig.HOST_URL + "/api/new_manpower/";

    /**
     * 用户协议
     */
    public static String AGREEMENT_URL = BuildConfig.HOST_URL + "/#/page/agreement";
    /**
     * 隐私权限
     */
    public static String PRIVATE_URL = BuildConfig.HOST_URL + "/#/page/privacy";

    /**
     *
     * 测试环境：https://stage-ttchain.tntlinking.com/
     * 生产环境：https://www.tntlinking.com/
     * 合作模式 ，服务手册，常见问题，活动介绍
     * https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md";
     */

    /**
     * 服务手册  /api/minio/manpower-pages/service_guide.md
     */
    public static String SERVICE_GUIDE_URL = BuildConfig.SERVICE_URL + "/api/minio/manpower-pages/service_guide.md";

    /**
     * 常见问题  /api/minio/manpower-pages/faq_guide.md
     */
    public static String FAQ_GUIDE_URL = BuildConfig.SERVICE_URL + "/api/minio/manpower-pages/faq_guide.md";
    /**
     * 合作模式   https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/recruit_guide.pdf
     */
    public static String RECRUIT_GUIDE_URL = BuildConfig.SERVICE_URL + "/api/minio/pdf/manpower-pages/recruit_guide.pdf";
    /**
     * 活动规则
     */
    public static String RECOMMEND_GUIDE_URL = BuildConfig.SERVICE_URL + "/api/minio/manpower-pages/recommend_guide.md";

    /**
     * 拍照技巧  https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/photography.md
     */
    public static String PHOTO_GUIDE_URL = BuildConfig.SERVICE_URL + "/api/minio/manpower-pages/photography.md";


    /**
     * 签约失败签约成功  https://talent.stage-ttchain.tntlinking.com/#/page/sign_contract
     */
    public static String SIGN_CONTRACT_URL = BuildConfig.SERVICE_URL + "/#/page/sign_contract  ";

    /**
     * 登陆时候需要的 请求头 写死的
     */
    public static String LOGIN_AUTHORIZATION = "Basic dG50bGlua2luZzptYW5wb3dlcg==";

    /**
     * access_token  登陆成功返回的token
     */
    public static String ACCESS_TOKEN = "access_token";

    /**
     * 开发者的id
     */
    public static String DEVELOPER_ID = "developer_id";
    /**
     * 是否登陆
     */
    public static String HAS_LOGIN = "has_login";

    /**
     * 是否认证  1->待认证  2->待审核   3->审核成功 4->审核失败
     */
    public static String DEVELOP_STATUS = "develop_status";

    /**
     * 开发者的手机号码
     */
    public static String DEVELOP_MOBILE = "mobile";
    /**
     * 开发者的名字
     */
    public static String DEVELOP_NAME = "develop_name";

    public static String CREATE_TIME = "create_time";
    public static String CAREER_ID = "career_id";
    public static String GUIDE_VIEW = "guide_view";

    /**
     * 友盟一键登录秘钥
     */
    public static String APP_SECRET = "zsiyoIhID4V/zG7ep9TMDRFlYwg/sHdC4Xj851YQplYTu7l3QNohhBoE5iDWBaMi" +
            "KxbLRquqkY5wdcTuIOZ4oPOjnX3SOyZJ+zGIrFojZjx5IIfD0lrXnPnEnKy/rwcnE1Vy+KMCGEYC43balOmA2fZzxyq8dUeK" +
            "eHOn0szVw3pWh5Ndx69KTAyU5pFyNM0sGdVL+vwrtrrv/BN7vFvRatDjfylsDDkLEwnMR4H/dZWvKt5ysUXigSW2wa0YXGWI/" +
            "FURiw+UMKN2t1r2rp6zdleCGti/D3FbKUIji0tKiLvlEzGUpwcd2YVUDQPffLup";

    /**
     * 签约服务弹框，只弹一次
     */
    public static String SERVICE_DIALOG = "service_dialog";

}
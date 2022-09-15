package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.tntlinking.tntdev.BuildConfig;

/**
 *    desc   : 获取验证码
 */
public final class GetSMSCodeApi implements IRequestServer,IRequestApi {

    @Override
    public String getHost() {  // /api/new_gateway/api/new_gateway/tntlinking-sso-authcenter/sms/send/1?mobile=18611150516
        return BuildConfig.HOST_URL +"/api/new_gateway/tntlinking-sso-authcenter/";
    }
    @Override
    public String getApi() {
        return "sms/send/1";
//        return "sms/send/verification/code";
    }

    /** 手机号 */
    private String mobile;

    public GetSMSCodeApi setPhone(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
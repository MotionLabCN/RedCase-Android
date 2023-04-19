package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpHeader;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.tntlinking.tntdev.BuildConfig;

/**
 *    desc   : 用户登录
 */
public final class LoginApi2 implements IRequestServer,IRequestApi {

    //http://localhost:8101/oauth/token?grant_type=sms_code&scope=all&smsCode=111459&mobile=17835378788&loginChannel=Developer_APP

    @Override
    public String getHost() {  // /api/new_gateway//tntlinking-sso-authcenter/oauth/token
        return BuildConfig.HOST_URL +"/api/new_gateway/tntlinking-sso-authcenter/";
    }
    @Override
    public String getApi() {
        return "oauth/token";
    }

    @HttpHeader
    private String Authorization;

    private String grant_type;
    private String scope;
    private String smsCode;
    private String mobile;
    private String loginChannel;
    private String register_platform;
    private String register_source;

    public LoginApi2 setAuthorization(String authorization) {
       this.Authorization = authorization;
       return this;
    }

    public LoginApi2 setGrant_type(String grant_type) {
        this.grant_type = grant_type;
        return this;
    }

    public LoginApi2 setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public LoginApi2 setSmsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }

    public LoginApi2 setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public LoginApi2 setLoginChannel(String loginChannel) {
        this.loginChannel = loginChannel;
        return this;
    }
    public LoginApi2 setRegister_platform(String register_platform) {
        this.register_platform = register_platform;
        return this;
    }

    public LoginApi2 setRegister_source(String register_source) {
        this.register_source = register_source;
        return this;
    }


    public final static class Bean {

        private String access_token;

        public String getAccessToken() {
            return access_token;
        }
    }
}
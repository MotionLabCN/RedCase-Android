package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpHeader;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;
import com.tntlinking.tntdev.BuildConfig;


public final class OneClickLoginApi implements IRequestServer,IRequestApi, IRequestType {

    @Override
    public String getHost() { // /api/new_gateway/tntlinking-sso-authcenter/login/one_key

        return BuildConfig.HOST_URL +"/api/new_gateway/tntlinking-sso-authcenter/";
    }
    @Override
    public String getApi() {
        return "login/one_key";
    }

    @HttpHeader
    private String Authorization;

    private String grant_type;
    private String invitationCode;
    private String loginChannel;
    private String message;
    private String mobile;
    private String scope;
    private String smsCode;
    private String youVerifyToken;
    private String register_platform;
    private String register_source;

    public OneClickLoginApi setAuthorization(String authorization) {
        this.Authorization = authorization;
        return this;
    }

    public OneClickLoginApi setGrant_type(String grant_type) {
        this.grant_type = grant_type;
        return this;
    }

    public OneClickLoginApi setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
        return this;
    }

    public OneClickLoginApi setLoginChannel(String loginChannel) {
        this.loginChannel = loginChannel;
        return this;

    }

    public OneClickLoginApi setMessage(String message) {
        this.message = message;
        return this;
    }

    public OneClickLoginApi setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public OneClickLoginApi setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public OneClickLoginApi setSmsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }

    public OneClickLoginApi setYouVerifyToken(String youVerifyToken) {
        this.youVerifyToken = youVerifyToken;
        return this;
    }

    public OneClickLoginApi setRegister_platform(String register_platform) {
        this.register_platform = register_platform;
        return this;
    }

    public OneClickLoginApi setRegister_source(String register_source) {
        this.register_source = register_source;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }


    public final static class Bean {

        private String access_token;

        public String getAccessToken() {
            return access_token;
        }
    }
}
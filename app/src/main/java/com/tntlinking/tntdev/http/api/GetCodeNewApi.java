package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

import java.util.List;


public final class GetCodeNewApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-sso-authcenter/sms/send/1";
    }

    private String mobile;

    public GetCodeNewApi setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class ChangeFirmPhoneApi implements IRequestApi  {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/member/modifyMobile";
    }

    private String verificationCode;
    private String mobile;


    public ChangeFirmPhoneApi setCode(String code) {
        this.verificationCode = code;
        return this;
    }

    public ChangeFirmPhoneApi setMobile(String mobile){
        this.mobile = mobile;
        return this;
    }

}
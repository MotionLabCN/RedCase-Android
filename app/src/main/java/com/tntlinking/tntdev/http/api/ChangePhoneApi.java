package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class ChangePhoneApi implements IRequestApi  {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/member/modifyMobile";
    }

    private String verificationCode;
    private String mobile;


    public ChangePhoneApi setCode(String code) {
        this.verificationCode = code;
        return this;
    }

    public ChangePhoneApi setMobile(String mobile){
        this.mobile = mobile;
        return this;
    }

}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class ChangePhoneApi implements IRequestApi , IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/update/mobile";
    }

    private String code;
    private String mobile;


    public ChangePhoneApi setCode(String code) {
        this.code = code;
        return this;
    }

    public ChangePhoneApi setMobile(String mobile){
        this.mobile = mobile;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
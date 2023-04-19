package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 * 升级企业账户
 */
public final class SendCompanyEmailApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/company/sendCompanyEmail";
    }


    private int companyId ;
    private String email;


    public SendCompanyEmailApi setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public SendCompanyEmailApi setEmail(String email) {
        this.email = email;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
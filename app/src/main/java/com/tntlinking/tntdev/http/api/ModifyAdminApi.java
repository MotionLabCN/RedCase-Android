package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

//移交管理员
public final class ModifyAdminApi implements IRequestApi{

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/modifyAdmin";
    }

    private int memberId;


    public ModifyAdminApi setMemberId(int memberId) {
        this.memberId = memberId;
        return this;
    }



}
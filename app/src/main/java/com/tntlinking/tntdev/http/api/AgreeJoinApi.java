package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

//同意加入公司
public final class AgreeJoinApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/agreeJoin/" + memberId;
    }


    @HttpIgnore
    private int memberId;

    public AgreeJoinApi setMemberId(int id) {
        this.memberId = id;
        return this;
    }


}
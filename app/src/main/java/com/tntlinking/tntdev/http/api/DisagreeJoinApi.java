package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

//拒绝加入公司
public final class DisagreeJoinApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/disagreeJoin/" + memberId;
    }


    @HttpIgnore
    private int memberId;

    public DisagreeJoinApi setMemberId(int id) {
        this.memberId = id;
        return this;
    }


}
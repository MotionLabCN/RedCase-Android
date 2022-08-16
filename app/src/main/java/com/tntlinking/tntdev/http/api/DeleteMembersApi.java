package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

//移出成员
public final class DeleteMembersApi implements IRequestApi {

    @Override
    public String getApi() { // tntlinking-member/sso/company/removeMember/{memberId}
        return "tntlinking-member/sso/company/removeMember/" + memberId;
    }


    @HttpIgnore
    private int memberId;

    public DeleteMembersApi setMemberId(int id) {
        this.memberId = id;
        return this;
    }


}
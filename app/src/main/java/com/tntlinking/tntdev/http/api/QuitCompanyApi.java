package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

//离开公司
public final class QuitCompanyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/company/quit";
    }


}
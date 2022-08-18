package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


//获取企业相关信息
public final class GetFirmInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/companyRecruiterAccount/getInfo";
    }


    public final static class Bean {

    }
}
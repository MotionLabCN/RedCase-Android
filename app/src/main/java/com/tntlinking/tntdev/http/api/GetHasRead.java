package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//获取是否有未读消息
public final class GetHasRead implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/jpush/getHasRead";
    }


}
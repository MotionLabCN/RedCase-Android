package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取开发者详情
public final class GetFirmDevDetailApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developerReveal/getDeveloperDetail";
    }


    private int developerId;


    public GetFirmDevDetailApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }


    public final static class Bean {


    }

}
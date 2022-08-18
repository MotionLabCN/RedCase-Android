package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//取消收藏开发者
public final class CancelCollectDeveloperApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/companyRecruiterFavorite/cancelFavorite";
    }


    private int developerId;


    public CancelCollectDeveloperApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }


    public final static class Bean {


    }

//    @Override
//    public BodyType getType() {
//        return BodyType.JSON;
//    }
}
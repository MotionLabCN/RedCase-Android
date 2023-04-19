package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//判断收藏状态
public final class CollectDeveloperStatusApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/companyRecruiterFavorite/judgeFavoriteStatus";
    }


    private int developerId;


    public CollectDeveloperStatusApi setDeveloperId(int developerId) {
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
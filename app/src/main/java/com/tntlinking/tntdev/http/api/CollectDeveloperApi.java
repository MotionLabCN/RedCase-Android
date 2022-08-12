package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;


public final class CollectDeveloperApi implements IRequestApi {

    @Override
    public String getApi() {
        return "companyRecruiterFavorite/favoriteDeveloper";
    }


    private int developerId;


    public CollectDeveloperApi setDeveloperId(int developerId) {
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
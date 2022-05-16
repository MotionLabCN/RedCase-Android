package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateServiceStatusApi implements IRequestApi , IRequestType{

    @Override
    public String getApi() {
        return "developer/updateServiceStatus";
    }


    private int type;  //切换状态 1：可接单 2：不接单

    public UpdateServiceStatusApi setType(int id) {
        this.type = id;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
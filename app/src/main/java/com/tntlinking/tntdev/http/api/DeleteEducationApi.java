package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class DeleteEducationApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer/delete/education/"+educationId;
    }


    @HttpIgnore
    private int educationId;

    public DeleteEducationApi setEducationId(int id) {
        this.educationId = id;
        return this;
    }


}
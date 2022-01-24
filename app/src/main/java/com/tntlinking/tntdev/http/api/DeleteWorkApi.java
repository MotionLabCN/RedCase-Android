package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class DeleteWorkApi implements IRequestApi{


    @Override
    public String getApi() {
        return "developer/delete/work/"+workExperienceId;
    }


    @HttpIgnore
    private int workExperienceId;

    public DeleteWorkApi setWorkExperienceId(int id) {
        this.workExperienceId = id;
        return this;
    }


}
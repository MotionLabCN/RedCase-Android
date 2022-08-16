package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class DeleteProjectApi implements IRequestApi{


    @Override
    public String getApi() {
        return "manpower-rest-api/developer/v2/delete/project/"+projectId;
    }


    @HttpIgnore
    private int projectId;

    public DeleteProjectApi setProjectId(int id) {
        this.projectId = id;
        return this;
    }


}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class AddWorkApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/add/work";
    }


    private int developerId;
    private String companyName;
    private int industryId;
    private String positionName;
    private String workStartTime;
    private String workEndTime;



    public AddWorkApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public AddWorkApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public AddWorkApi setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public AddWorkApi setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
        return this;
    }

    public AddWorkApi setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
        return this;
    }

    public AddWorkApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
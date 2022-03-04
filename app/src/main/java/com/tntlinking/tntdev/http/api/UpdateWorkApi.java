package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateWorkApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/update/work";
    }


    private int developerId;
    private String companyName;
    private int industryId;
    private String positionName;
    private String workStartTime;
    private String workEndTime;
    private int workExperienceId;


    public UpdateWorkApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public UpdateWorkApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public UpdateWorkApi setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public UpdateWorkApi setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
        return this;
    }

    public UpdateWorkApi setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
        return this;
    }

    public UpdateWorkApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }

    public UpdateWorkApi setWorkExperienceId(int workExperienceId) {
        this.workExperienceId = workExperienceId;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
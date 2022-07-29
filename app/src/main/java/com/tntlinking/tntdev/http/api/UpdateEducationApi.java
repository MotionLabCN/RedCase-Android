package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateEducationApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
//        return "developer/update/education";
        return "developer/v2/save/education";
    }


    private String id;//教育经历ID  // id传null或者传0 是新增，其他是更新修改
    private String collegeName; //院校名称
    private int educationId;//学历id
    private String inSchoolEndTime;//在校结束时间
    private String inSchoolStartTime;//在校开始时间
    private String major;//专业id
    private int trainingMode;//培养方式id


    public UpdateEducationApi setId(String id) {
        this.id = id;
        return this;
    }

    public UpdateEducationApi setEducationId(int educationId) {
        this.educationId = educationId;
        return this;
    }

    public UpdateEducationApi setCollegeName(String collegeName) {
        this.collegeName = collegeName;
        return this;
    }

    public UpdateEducationApi setMajor(String major) {
        this.major = major;
        return this;
    }

    public UpdateEducationApi setInSchoolStartTime(String inSchoolStartTime) {
        this.inSchoolStartTime = inSchoolStartTime;
        return this;
    }

    public UpdateEducationApi setInSchoolEndTime(String inSchoolEndTime) {
        this.inSchoolEndTime = inSchoolEndTime;
        return this;
    }

    public UpdateEducationApi setTrainingMode(int trainingMode) {
        this.trainingMode = trainingMode;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
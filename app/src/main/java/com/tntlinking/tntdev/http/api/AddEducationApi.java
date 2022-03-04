package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class AddEducationApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/add/education";
    }


//    private int id;//教育经历ID
    private String collegeName; //院校名称
    private int educationId;//学历id
    private String inSchoolEndTime;//在校结束时间
    private String inSchoolStartTime;//在校开始时间
    private String major;//专业id
    private int trainingMode;//培养方式id

//    public AddEducationApi setId(int id) {
//        this.id = id;
//        return  this;
//    }

    public AddEducationApi setEducationId(int educationId) {
        this.educationId = educationId;
        return  this;
    }

    public AddEducationApi setCollegeName(String collegeName) {
        this.collegeName = collegeName;
        return  this;
    }

    public AddEducationApi setMajor(String major) {
        this.major = major;
        return  this;
    }

    public AddEducationApi setInSchoolStartTime(String inSchoolStartTime) {
        this.inSchoolStartTime = inSchoolStartTime;
        return  this;
    }

    public AddEducationApi setInSchoolEndTime(String inSchoolEndTime) {
        this.inSchoolEndTime = inSchoolEndTime;
        return  this;
    }

    public AddEducationApi setTrainingMode(int trainingMode) {
        this.trainingMode = trainingMode;
        return  this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;


public final class UpdatePositionApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/position/update";
    }

    private int id;//职位id
    private int careerDirectionId;   //开发者类型(对应字典表id)
    private String title;//职位名称
    private String description;//职位描述
    private String recruitCount;    //需求数
    private int workOperId;    //工作方式：远程办公(对应字典表id)
    private int workYearsId;    //工作年限要求(对应字典表id)
    private int educationId;    //学历要求(对应字典表id)
    private int trainingModeId;    //学历性质(对应字典表id)
    private int workDaysMode;    //工作方式要求(1-全日 2-半日)
    private String startPay;    //	薪资范围-开始薪资
    private String endPay;    //	薪资范围-开始薪资
    private int industryMandatory = 1;    //行业强制性
    private List<Integer> skillIds;//技能id

    public UpdatePositionApi setId(int id) {
        this.id = id;
        return this;
    }

    public UpdatePositionApi setCareerDirectionId(int careerDirectionId) {
        this.careerDirectionId = careerDirectionId;
        return this;
    }

    public UpdatePositionApi setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdatePositionApi setDescription(String description) {
        this.description = description;
        return this;
    }

    public UpdatePositionApi setRecruitCount(String recruitCount) {
        this.recruitCount = recruitCount;
        return this;
    }

    public UpdatePositionApi setWorkOperId(int workOperId) {
        this.workOperId = workOperId;
        return this;
    }

    public UpdatePositionApi setWorkYearsId(int workYearsId) {
        this.workYearsId = workYearsId;
        return this;
    }

    public UpdatePositionApi setEducationId(int educationId) {
        this.educationId = educationId;
        return this;
    }

    public UpdatePositionApi setTrainingModeId(int trainingModeId) {
        this.trainingModeId = trainingModeId;
        return this;
    }

    public UpdatePositionApi setWorkDaysMode(int workDaysMode) {
        this.workDaysMode = workDaysMode;
        return this;
    }

    public UpdatePositionApi setStartPay(String startPay) {
        this.startPay = startPay;
        return this;
    }

    public UpdatePositionApi setEndPay(String endPay) {
        this.endPay = endPay;
        return this;
    }

    public UpdatePositionApi setIndustryMandatory(int industryMandatory) {
        this.industryMandatory = industryMandatory;
        return this;
    }

    public UpdatePositionApi setSkillIds(List<Integer> skillIds) {
        this.skillIds = skillIds;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;


public final class UpdateProjectApi implements IRequestApi, IRequestType {


    @Override
    public String getApi() {
        return "developer/v2/save/project";
    }


    public String id;
    private String companyName; //公司名称
    private String description;//项目描述
    private int industryId;//行业id
    private String position;//职位/角色
    private String projectEndDate;//项目结束时间
    private String projectStartDate;//项目开始时间
    private String projectName;//项目名称
    private List<Integer> skillIdList;//技能id
    private int workMode;//职业状态id

    public UpdateProjectApi setId(String id) {
        this.id = id;
        return this;
    }

    public UpdateProjectApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public UpdateProjectApi setDescription(String description) {
        this.description = description;
        return this;
    }

    public UpdateProjectApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }

    public UpdateProjectApi setPosition(String position) {
        this.position = position;
        return this;
    }

    public UpdateProjectApi setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
        return this;
    }

    public UpdateProjectApi setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
        return this;
    }

    public UpdateProjectApi setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public UpdateProjectApi setWorkMode(int workModeId) {
        this.workMode = workModeId;
        return this;
    }

    public UpdateProjectApi setSkillIdList(List<Integer> skillIdList) {
        this.skillIdList = skillIdList;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
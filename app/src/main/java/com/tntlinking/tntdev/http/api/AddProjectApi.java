package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;


public final class AddProjectApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/project";
    }


    private String companyName; //公司名称
    private String description;//项目描述
    private int industryId;//行业id
    private String position;//职位/角色
    private String projectEndDate;//项目结束时间
    private String projectStartDate;//项目开始时间
    private String projectName;//项目名称
    private List<Integer> skillIdList;//技能id
    private int workModeId;//职业状态id


    public AddProjectApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public AddProjectApi setDescription(String description) {
        this.description = description;
        return this;
    }

    public AddProjectApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }

    public AddProjectApi setPosition(String position) {
        this.position = position;
        return this;
    }

    public AddProjectApi setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
        return this;
    }

    public AddProjectApi setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
        return this;
    }

    public AddProjectApi setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public AddProjectApi setWorkModeId(int workModeId) {
        this.workModeId = workModeId;
        return this;
    }

    public AddProjectApi setSkillIdList(List<Integer> skillIdList) {
        this.skillIdList = skillIdList;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
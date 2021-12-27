package com.tntlinking.tntdev.ui.bean;

import com.tntlinking.tntdev.http.api.GetTagListApi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dan on 2021/12/3.
 */

public class ExperienceBean implements Serializable {
    private int type;

    private String collegeName; //学校名字
    private String education;//学历
    private int educationId;//学历id
    private String major;//专业
    private String inSchoolStartTime;//入学时间
    private String inSchoolEndTime;//毕业时间
    private String mTrainingMode;//培养方式
    private int trainingMode;//培养方式id



    private String companyName; //公司名称
    private String description;//项目描述
    private int industryId;//行业id
    private String position;//职位/角色
    private String projectEndDate;//项目结束时间
    private String projectStartDate;//项目开始时间
    private String projectName;//项目名称
    private int workMode;//职业状态id
    private String industry;//行业
    private String mWorkMode;//职业状态
    private List<GetTagListApi.Bean.ChildrenBean> skillList;//技能
    private List<Integer> skillIdList;//技能id

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInSchoolStartTime() {
        return inSchoolStartTime;
    }

    public void setInSchoolStartTime(String inSchoolStartTime) {
        this.inSchoolStartTime = inSchoolStartTime;
    }

    public String getInSchoolEndTime() {
        return inSchoolEndTime;
    }

    public void setInSchoolEndTime(String inSchoolEndTime) {
        this.inSchoolEndTime = inSchoolEndTime;
    }

    public String getmTrainingMode() {
        return mTrainingMode;
    }

    public void setmTrainingMode(String mTrainingMode) {
        this.mTrainingMode = mTrainingMode;
    }

    public int getTrainingMode() {
        return trainingMode;
    }

    public void setTrainingMode(int trainingMode) {
        this.trainingMode = trainingMode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        this.workMode = workMode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getmWorkMode() {
        return mWorkMode;
    }

    public void setmWorkMode(String mWorkMode) {
        this.mWorkMode = mWorkMode;
    }

    public List<GetTagListApi.Bean.ChildrenBean> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<GetTagListApi.Bean.ChildrenBean> skillList) {
        this.skillList = skillList;
    }

    public List<Integer> getSkillIdList() {
        return skillIdList;
    }

    public void setSkillIdList(List<Integer> skillIdList) {
        this.skillIdList = skillIdList;
    }

    public ExperienceBean(int type) {
        this.type = type;
    }

    public ExperienceBean() {
    }
}

package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;
import java.util.List;


public final class SendDeveloperApi implements IRequestApi , IRequestType {

    @Override
    public String getApi() {
        return "developer/settled";
    }

    private String realName = "";//名字
    private int sex = 0; //性别 0->男 1->女
    private String birthday = "2020.2.1";
    private int provinceId = 1;// 省
    private int cityId = 1;// 城市
    private int areasId = 1;// 区
    private int remoteWorkReason = 1;//


    private int careerDirectionId = 1;    //职业方向id
    private int workYearsId = 1;    //工作年限id
    private String curSalary = "1";    //当前薪资
    private int workDayMode = 1; // 1 全日 2 半日
    private String lowestSalary = "1"; // 期望最低薪资
    private String highestSalary = "1"; // 期望最高薪资
    private String workYears = ""; //工作年限

    private List<DeveloperEducation> developerEducationParamList;
    private List<DeveloperProject> developerProjectParamList;


    public SendDeveloperApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public SendDeveloperApi setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public SendDeveloperApi setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public SendDeveloperApi setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public SendDeveloperApi setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public SendDeveloperApi setAreasId(int areasId) {
        this.areasId = areasId;
        return this;
    }

    public SendDeveloperApi setRemoteWorkReason(int remoteWorkReason) {
        this.remoteWorkReason = remoteWorkReason;
        return this;
    }

    public SendDeveloperApi setCareerDirectionId(int careerDirectionId) {
        this.careerDirectionId = careerDirectionId;
        return this;
    }

    public SendDeveloperApi setWorkYearsId(int workYearsId) {
        this.workYearsId = workYearsId;
        return this;
    }

    public SendDeveloperApi setCurSalary(String curSalary) {
        this.curSalary = curSalary;
        return this;
    }

    public SendDeveloperApi setWorkDayMode(int workDayMode) {
        this.workDayMode = workDayMode;
        return this;
    }


    public SendDeveloperApi setLowestSalary(String lowestSalary) {
        this.lowestSalary = lowestSalary;
        return this;
    }

    public SendDeveloperApi setHighestSalary(String highestSalary) {
        this.highestSalary = highestSalary;
        return this;
    }

    public SendDeveloperApi setworkYears(String workYears) {
        this.workYears = workYears;
        return this;
    }

    public SendDeveloperApi setDeveloperEducationParamList(List<DeveloperEducation> developerEducationParamList) {
        this.developerEducationParamList = developerEducationParamList;
        return this;
    }


    public SendDeveloperApi setDeveloperProjectParamList(List<DeveloperProject> developerProjectParamList) {
        this.developerProjectParamList = developerProjectParamList;
        return this;
    }

    public static class DeveloperEducation {
        private String collegeName; //院校名称
        private int educationId;//学历id
        private String inSchoolEndTime;//在校结束时间
        private String inSchoolStartTime;//在校开始时间
        private String major;//专业id
        private int trainingMode;//培养方式id




        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }


        public int getEducationId() {
            return educationId;
        }

        public void setEducationId(int educationId) {
            this.educationId = educationId;
        }

        public String getInSchoolEndTime() {
            return inSchoolEndTime;
        }

        public void setInSchoolEndTime(String inSchoolEndTime) {
            this.inSchoolEndTime = inSchoolEndTime;
        }

        public String getInSchoolStartTime() {
            return inSchoolStartTime;
        }

        public void setInSchoolStartTime(String inSchoolStartTime) {
            this.inSchoolStartTime = inSchoolStartTime;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public int getTrainingMode() {
            return trainingMode;
        }

        public void setTrainingMode(int trainingMode) {
            this.trainingMode = trainingMode;
        }


    }


    public static class DeveloperProject {
        private String companyName; //公司名称
        private String description;//项目描述
        private int industryId;//行业id
        private String position;//职位/角色
        private String projectEndDate;//项目结束时间
        private String projectStartDate;//项目开始时间
        private String projectName;//项目名称
        private List<Integer> skillIdList;//技能id
        private int workMode;//职业状态id



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

        public List<Integer> getSkillIdList() {
            return skillIdList;
        }

        public void setSkillIdList(List<Integer> skillIdList) {
            this.skillIdList = skillIdList;
        }

        public int getWorkMode() {
            return workMode;
        }

        public void setWorkMode(int workMode) {
            this.workMode = workMode;
        }


    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
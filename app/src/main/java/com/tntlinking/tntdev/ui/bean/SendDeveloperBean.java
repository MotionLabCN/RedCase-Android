package com.tntlinking.tntdev.ui.bean;

import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.api.SendDeveloperApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 2021/12/6.
 */

public class SendDeveloperBean implements Serializable {
    private String realName = "";//名字
    private int sex = 0; //性别 0->男 1->女
    private String mSex = "";
    private String birthday ="";
    private int provinceId = 1;// 省
    private int cityId = 1;// 城市
    private int areasId = 1;// 区
    private int remoteWorkReason = 1;//远程办公原因id
    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";
    private String mRemoteWorkReason = "";//远程办公原因

    private int careerDirectionId = 163; //职业方向id
    private int workYearsId;    //工作年限id
    private String curSalary;    //当前薪资
    private int workDayMode = 1; // 1 全日 2 半日  //默认全日
    private String lowestSalary; // 期望最低薪资
    private String highestSalary; // 期望最高薪资
    private String careerDirection; //职业方向
    private String workYears; //工作年限


    private List<SendDeveloperApi.DeveloperEducation> developerEducationParamList = new ArrayList<>();
    private List<SendDeveloperApi.DeveloperProject> developerProjectParamList = new ArrayList<>();

    public static class DeveloperEducation {
        private String collegeName; //院校名称
        private int educationId;//学历id
        private String inSchoolEndTime;//在校结束时间
        private String inSchoolStartTime;//在校开始时间
        private String major;//专业
        private int trainingMode;//培养方式id
        private String education; //学历
        private String mTrainingMode;//培养方式

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

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getmTrainingMode() {
            return mTrainingMode;
        }

        public void setmTrainingMode(String mTrainingMode) {
            this.mTrainingMode = mTrainingMode;
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

        private String industry;//行业
        private int mWorkMode;//职业状态
        private List<GetTagListApi.Bean.ChildrenBean> skillList;//技能

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

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public int getmWorkMode() {
            return mWorkMode;
        }

        public void setmWorkMode(int mWorkMode) {
            this.mWorkMode = mWorkMode;
        }

        public List<GetTagListApi.Bean.ChildrenBean> getSkillList() {
            return skillList;
        }

        public void setSkillList(List<GetTagListApi.Bean.ChildrenBean> skillList) {
            this.skillList = skillList;
        }
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreasId() {
        return areasId;
    }

    public void setAreasId(int areasId) {
        this.areasId = areasId;
    }

    public int getRemoteWorkReason() {
        return remoteWorkReason;
    }

    public void setRemoteWorkReason(int remoteWorkReason) {
        this.remoteWorkReason = remoteWorkReason;
    }

    public int getCareerDirectionId() {
        return careerDirectionId;
    }

    public void setCareerDirectionId(int careerDirectionId) {
        this.careerDirectionId = careerDirectionId;
    }

    public int getWorkYearsId() {
        return workYearsId;
    }

    public void setWorkYearsId(int workYearsId) {
        this.workYearsId = workYearsId;
    }

    public String getCurSalary() {
        return curSalary;
    }

    public void setCurSalary(String curSalary) {
        this.curSalary = curSalary;
    }

    public int getWorkDayMode() {
        return workDayMode;
    }

    public void setWorkDayMode(int workDayMode) {
        this.workDayMode = workDayMode;
    }

    public String getLowestSalary() {
        return lowestSalary;
    }

    public void setLowestSalary(String lowestSalary) {
        this.lowestSalary = lowestSalary;
    }

    public String getHighestSalary() {
        return highestSalary;
    }

    public void setHighestSalary(String highestSalary) {
        this.highestSalary = highestSalary;
    }



    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmArea() {
        return mArea;
    }

    public void setmArea(String mArea) {
        this.mArea = mArea;
    }

    public String getmRemoteWorkReason() {
        return mRemoteWorkReason;
    }

    public void setmRemoteWorkReason(String mRemoteWorkReason) {
        this.mRemoteWorkReason = mRemoteWorkReason;
    }

    public String getCareerDirection() {
        return careerDirection;
    }

    public void setCareerDirection(String careerDirection) {
        this.careerDirection = careerDirection;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public List<SendDeveloperApi.DeveloperEducation> getDeveloperEducationParamList() {
        return developerEducationParamList;
    }

    public void setDeveloperEducationParamList(List<SendDeveloperApi.DeveloperEducation> developerEducationParamList) {
        this.developerEducationParamList = developerEducationParamList;
    }

    public List<SendDeveloperApi.DeveloperProject> getDeveloperProjectParamList() {
        return developerProjectParamList;
    }

    public void setDeveloperProjectParamList(List<SendDeveloperApi.DeveloperProject> developerProjectParamList) {
        this.developerProjectParamList = developerProjectParamList;
    }


    private volatile static SendDeveloperBean instance; //声明成 volatile

    private SendDeveloperBean() {
    }

    public static SendDeveloperBean getSingleton() {
        if (instance == null) {
            synchronized (SendDeveloperBean.class) {
                if (instance == null) {
                    instance = new SendDeveloperBean();
                }
            }
        }
        return instance;
    }

}

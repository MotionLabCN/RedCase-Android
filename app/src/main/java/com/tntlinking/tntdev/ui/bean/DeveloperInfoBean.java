package com.tntlinking.tntdev.ui.bean;


import com.tntlinking.tntdev.http.api.GetTagListApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 2021/12/6.
 */

public class DeveloperInfoBean implements Serializable {
    private int id;
    private String realName;//名字
    private int status;
    private int sex = 0; //性别 0->男 1->女
    private String mSex;
    private String birthday;
    private String avatarUrl;
    private int provinceId = 1;// 省
    private int cityId = 1;// 城市
    private int areasId = 1;// 区
    private String provinceName;
    private String cityName;
    private String areasName;
    private int remoteWorkReason = 1;//远程办公原因id
    private String remoteWorkReasonStr;//远程办公原因


    private int careerDirectionId = 163; //职业方向id
    private int workYearsId;    //工作年限id
    private String curSalary;    //当前薪资
    private int workDayMode = 1; // 1 全日 2 半日  //默认全日
    private String lowestSalary; // 期望最低薪资
    private String highestSalary; // 期望最高薪资
    private String expectSalary; //薪资
    private String careerDirection; //职业方向
    private String workYears; //工作年限

    private DeveloperCareer careerDto;
    private List<WorkMode> workModeDtoList = new ArrayList<>();
    private List<DeveloperEducation> educationDtoList = new ArrayList<>();
    private List<DeveloperProject> projectDtoList = new ArrayList<>();
    private List<DeveloperWork> workExperienceDtoList = new ArrayList<>();
    private List<DeveloperSkillDto> developerSkillDtoList = new ArrayList<>();

    public static class DeveloperCareer implements Serializable {
        private int careerDirectionId = 163; //职业方向id
        private String careerDirectionName; //职业方向id
        private int workYearsId;    //工作年限id
        private String curSalary;    //当前薪资
        private int workDayMode = 1; // 1 全日 2 半日  //默认全日
        private String lowestSalary; // 期望最低薪资
        private String highestSalary; // 期望最高薪资
        private String expectSalary; //薪资

        private String workYearsName; //工作年限

        public int getCareerDirectionId() {
            return careerDirectionId;
        }

        public void setCareerDirectionId(int careerDirectionId) {
            this.careerDirectionId = careerDirectionId;
        }

        public String getCareerDirectionName() {
            return careerDirectionName;
        }

        public void setCareerDirectionName(String careerDirectionName) {
            this.careerDirectionName = careerDirectionName;
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

        public String getExpectSalary() {
            return expectSalary;
        }

        public void setExpectSalary(String expectSalary) {
            this.expectSalary = expectSalary;
        }

        public String getWorkYearsName() {
            return workYearsName;
        }

        public void setWorkYearsName(String workYears) {
            this.workYearsName = workYears;
        }
    }


    public static class WorkMode implements Serializable {
        private int id;
        private String expectSalary;    //当前薪资
        private String lowestSalary;    // 期望最低薪资
        private String highestSalary;    // 期望最高薪资
        private int workDayMode = 1; // 1 全日 2 半日  //默认全日
        private String workDayModeName;   //默认全日
        private String workDayModeDesc; //"日均工作8小时 / 每周工作40小时"

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExpectSalary() {
            return expectSalary;
        }

        public void setExpectSalary(String expectSalary) {
            this.expectSalary = expectSalary;
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

        public int getWorkDayMode() {
            return workDayMode;
        }

        public void setWorkDayMode(int workDayMode) {
            this.workDayMode = workDayMode;
        }

        public String getWorkDayModeName() {
            return workDayModeName;
        }

        public void setWorkDayModeName(String workDayModeName) {
            this.workDayModeName = workDayModeName;
        }

        public String getWorkDayModeDesc() {
            return workDayModeDesc;
        }

        public void setWorkDayModeDesc(String workDayModeDesc) {
            this.workDayModeDesc = workDayModeDesc;
        }
    }

    public static class DeveloperEducation implements Serializable {
        private int id;
        private int developerId;
        private String collegeName; //院校名称
        private String inSchoolEndTime;//在校结束时间
        private String inSchoolStartTime;//在校开始时间
        private String major;//专业
        private int educationId;//学历id
        private String educationName; //学历
        private int trainingMode;//培养方式id
        private String trainingModeName;//培养方式

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
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

        public int getEducationId() {
            return educationId;
        }

        public void setEducationId(int educationId) {
            this.educationId = educationId;
        }

        public String getEducationName() {
            return educationName;
        }

        public void setEducationName(String education) {
            this.educationName = education;
        }

        public int getTrainingMode() {
            return trainingMode;
        }

        public void setTrainingMode(int trainingMode) {
            this.trainingMode = trainingMode;
        }

        public String getTrainingModeName() {
            return trainingModeName;
        }

        public void setTrainingModeName(String trainingModeName) {
            this.trainingModeName = trainingModeName;
        }
    }


    public static class DeveloperProject implements Serializable {
        private int id;
        private int developerId;
        private String companyName; //公司名称
        private String description;//项目描述
        private int industryId;//行业id
        private String industryName;//行业
        private String position;//职位/角色
        private String projectStartDate;//项目开始时间
        private String projectEndDate;//项目结束时间

        private String projectName;//项目名称
        private int workMode;//职业状态id
        private String workModeName;//职业状态
        private List<GetTagListApi.Bean.ChildrenBean> projectSkillList;//技能id


        private static class Skill {
            private int id;
            private int projectId;
            private int skillId;
            private String skillName; //公司名称

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProjectId() {
                return projectId;
            }

            public void setProjectId(int projectId) {
                this.projectId = projectId;
            }

            public int getSkillId() {
                return skillId;
            }

            public void setSkillId(int skillId) {
                this.skillId = skillId;
            }

            public String getSkillName() {
                return skillName;
            }

            public void setSkillName(String skillName) {
                this.skillName = skillName;
            }
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
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

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getProjectStartDate() {
            return projectStartDate;
        }

        public void setProjectStartDate(String projectStartDate) {
            this.projectStartDate = projectStartDate;
        }

        public String getProjectEndDate() {
            return projectEndDate;
        }

        public void setProjectEndDate(String projectEndDate) {
            this.projectEndDate = projectEndDate;
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

        public String getWorkModeName() {
            return workModeName;
        }

        public void setWorkModeName(String workModeName) {
            this.workModeName = workModeName;
        }

        public List<GetTagListApi.Bean.ChildrenBean> getProjectSkillList() {
            return projectSkillList;
        }

        public void setProjectSkillList(List<GetTagListApi.Bean.ChildrenBean> projectSkillList) {
            this.projectSkillList = projectSkillList;
        }
    }

    public static class DeveloperWork implements Serializable {

        private int id;
        private int developerId;
        private String companyName; //公司名称
        private int industryId;//行业id
        private String industryName;//行业
        private String positionName;//职位/角色
        private String workStartTime;//项目开始时间
        private String workEndTime;//项目结束时间


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getIndustryId() {
            return industryId;
        }

        public void setIndustryId(int industryId) {
            this.industryId = industryId;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getWorkStartTime() {
            return workStartTime;
        }

        public void setWorkStartTime(String workStartTime) {
            this.workStartTime = workStartTime;
        }

        public String getWorkEndTime() {
            return workEndTime;
        }

        public void setWorkEndTime(String workEndTime) {
            this.workEndTime = workEndTime;
        }
    }

    public static class DeveloperSkillDto implements Serializable {

        private int id;
        private int developerId;
        private int skillId;
        private String skillName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public int getSkillId() {
            return skillId;
        }

        public void setSkillId(int skillId) {
            this.skillId = skillId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreasName() {
        return areasName;
    }

    public void setAreasName(String areasName) {
        this.areasName = areasName;
    }

    public int getRemoteWorkReason() {
        return remoteWorkReason;
    }

    public void setRemoteWorkReason(int remoteWorkReason) {
        this.remoteWorkReason = remoteWorkReason;
    }

    public String getRemoteWorkReasonStr() {
        return remoteWorkReasonStr;
    }

    public void setRemoteWorkReasonStr(String remoteWorkReasonStr) {
        this.remoteWorkReasonStr = remoteWorkReasonStr;
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

    public DeveloperCareer getCareerDto() {
        return careerDto;
    }

    public void setCareerDto(DeveloperCareer careerDto) {
        this.careerDto = careerDto;
    }

    public List<WorkMode> getWorkModeDtoList() {
        return workModeDtoList;
    }

    public void setWorkModeDtoList(List<WorkMode> workModeDtoList) {
        this.workModeDtoList = workModeDtoList;
    }

    public List<DeveloperEducation> getEducationDtoList() {
        return educationDtoList;
    }

    public void setEducationDtoList(List<DeveloperEducation> educationDtoList) {
        this.educationDtoList = educationDtoList;
    }

    public List<DeveloperProject> getProjectDtoList() {
        return projectDtoList;
    }

    public void setProjectDtoList(List<DeveloperProject> projectDtoList) {
        this.projectDtoList = projectDtoList;
    }

    public List<DeveloperWork> getWorkExperienceDtoList() {
        return workExperienceDtoList;
    }

    public void setWorkExperienceDtoList(List<DeveloperWork> workExperienceDtoList) {
        this.workExperienceDtoList = workExperienceDtoList;
    }

    public List<DeveloperSkillDto> getDeveloperSkillDtoList() {
        return developerSkillDtoList;
    }

    public void setDeveloperSkillDtoList(List<DeveloperSkillDto> developerSkillDtoList) {
        this.developerSkillDtoList = developerSkillDtoList;
    }

    public void setDeveloperInfoBean(DeveloperInfoBean bean) {
        this.instance = bean;
    }

    private volatile static DeveloperInfoBean instance; //声明成 volatile

    private DeveloperInfoBean() {
    }

    public static DeveloperInfoBean getSingleton() {
        if (instance == null) {
            synchronized (DeveloperInfoBean.class) {
                if (instance == null) {
                    instance = new DeveloperInfoBean();
                }
            }
        }
        return instance;
    }

}

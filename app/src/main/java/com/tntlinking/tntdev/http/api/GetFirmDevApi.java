package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取开发者列表
public final class GetFirmDevApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developerReveal/getDeveloper";
    }


    private int careerIds;
    private int pageNum;
    private int pageSize;

    public GetFirmDevApi setOrderId(int careerIds) {
        this.careerIds = careerIds;
        return this;
    }

    public GetFirmDevApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmDevApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {

            private int total;
            private int pageNum;
            private List<ListBean> list;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String realName;
                private String mobile;
                private String avatarUrl;
                private int careerDirectionId;
                private String careerDirectionName;
                private String cityName;
                private double curSalary;
                private int workYearsId;
                private String workYearsName;
                private String skillName;
                private double expectSalary;
                private int workDayMode;
                private String workDayModeName;
                private int educationId;
                private String educationName;

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

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }

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

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public double getCurSalary() {
                    return curSalary;
                }

                public void setCurSalary(double curSalary) {
                    this.curSalary = curSalary;
                }

                public int getWorkYearsId() {
                    return workYearsId;
                }

                public void setWorkYearsId(int workYearsId) {
                    this.workYearsId = workYearsId;
                }

                public String getWorkYearsName() {
                    return workYearsName;
                }

                public void setWorkYearsName(String workYearsName) {
                    this.workYearsName = workYearsName;
                }

                public String getSkillName() {
                    return skillName;
                }

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public double getExpectSalary() {
                    return expectSalary;
                }

                public void setExpectSalary(double expectSalary) {
                    this.expectSalary = expectSalary;
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

                public int getEducationId() {
                    return educationId;
                }

                public void setEducationId(int educationId) {
                    this.educationId = educationId;
                }

                public String getEducationName() {
                    return educationName;
                }

                public void setEducationName(String educationName) {
                    this.educationName = educationName;
                }
            }
        }

}
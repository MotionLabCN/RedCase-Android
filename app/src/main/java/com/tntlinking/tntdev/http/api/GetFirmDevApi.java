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

            private Integer total;
            private Integer pageNum;
            private List<ListBean> list;

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public Integer getPageNum() {
                return pageNum;
            }

            public void setPageNum(Integer pageNum) {
                this.pageNum = pageNum;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private Integer id;
                private String realName;
                private String mobile;
                private String avatarUrl;
                private Integer careerDirectionId;
                private String careerDirectionName;
                private String cityName;
                private Double curSalary;
                private Integer workYearsId;
                private String workYearsName;
                private String skillName;
                private Double expectSalary;
                private Integer workDayMode;
                private String workDayModeName;
                private Integer educationId;
                private String educationName;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
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

                public Integer getCareerDirectionId() {
                    return careerDirectionId;
                }

                public void setCareerDirectionId(Integer careerDirectionId) {
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

                public Double getCurSalary() {
                    return curSalary;
                }

                public void setCurSalary(Double curSalary) {
                    this.curSalary = curSalary;
                }

                public Integer getWorkYearsId() {
                    return workYearsId;
                }

                public void setWorkYearsId(Integer workYearsId) {
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

                public Double getExpectSalary() {
                    return expectSalary;
                }

                public void setExpectSalary(Double expectSalary) {
                    this.expectSalary = expectSalary;
                }

                public Integer getWorkDayMode() {
                    return workDayMode;
                }

                public void setWorkDayMode(Integer workDayMode) {
                    this.workDayMode = workDayMode;
                }

                public String getWorkDayModeName() {
                    return workDayModeName;
                }

                public void setWorkDayModeName(String workDayModeName) {
                    this.workDayModeName = workDayModeName;
                }

                public Integer getEducationId() {
                    return educationId;
                }

                public void setEducationId(Integer educationId) {
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
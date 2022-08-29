package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

//收藏开发者列表
public final class GetFavoriteDeveloperApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/companyRecruiterFavorite/page/getFavoriteDeveloper";
    }


    private int pageNum;
    private int pageSize = 20;


    public GetFavoriteDeveloperApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFavoriteDeveloperApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }


    public final static class Bean {

        private Integer total;
        private Integer pageNum;
        private List<GetFavoriteDeveloperApi.Bean.ListBean> list;

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

        public List<GetFavoriteDeveloperApi.Bean.ListBean> getList() {
            return list;
        }

        public void setList(List<GetFavoriteDeveloperApi.Bean.ListBean> list) {
            this.list = list;
        }

        public static class ListBean  implements Serializable {

            private int developerId;
            private String avatarUrl;
            private String realName;
            private int status;
            private String workMode;
            private double expectSalary;
            private String careerDirectionName;
            private String workYears;
            private String education;
            private List<String> skillNameList;

            public int getDeveloperId() {
                return developerId;
            }

            public void setDeveloperId(int developerId) {
                this.developerId = developerId;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
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

            public String getWorkMode() {
                return workMode;
            }

            public void setWorkMode(String workMode) {
                this.workMode = workMode;
            }

            public double getExpectSalary() {
                return expectSalary;
            }

            public void setExpectSalary(double expectSalary) {
                this.expectSalary = expectSalary;
            }

            public String getCareerDirectionName() {
                return careerDirectionName;
            }

            public void setCareerDirectionName(String careerDirectionName) {
                this.careerDirectionName = careerDirectionName;
            }

            public String getWorkYears() {
                return workYears;
            }

            public void setWorkYears(String workYears) {
                this.workYears = workYears;
            }

            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;
            }

            public List<String> getSkillNameList() {
                return skillNameList;
            }

            public void setSkillNameList(List<String> skillNameList) {
                this.skillNameList = skillNameList;
            }
        }
    }
}
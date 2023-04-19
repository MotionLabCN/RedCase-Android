package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//APP端职位自荐的开发者(分页)
public final class GetFirmSelfRecommendsApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/position/app/self_recommends";
    }


    private int positionId;
    private int pageNum;
    private int pageSize = 20;

    public GetFirmSelfRecommendsApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public GetFirmSelfRecommendsApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmSelfRecommendsApi setPageSize(int pageSize) {
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
            private int id;
            private String realName;
            private String avatarUrl;
            private String careerDirectionName;
            private String workYearsName;
            private String educationName;
            private String trainingModeName;
            private Boolean isOperationRecommendation;
            private int positionId;
            private String expectSalary;
            private String createDate;
            private int tag;

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

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getCareerDirectionName() {
                return careerDirectionName;
            }

            public void setCareerDirectionName(String careerDirectionName) {
                this.careerDirectionName = careerDirectionName;
            }

            public String getWorkYearsName() {
                return workYearsName;
            }

            public void setWorkYearsName(String workYearsName) {
                this.workYearsName = workYearsName;
            }

            public String getEducationName() {
                return educationName;
            }

            public void setEducationName(String educationName) {
                this.educationName = educationName;
            }

            public String getTrainingModeName() {
                return trainingModeName;
            }

            public void setTrainingModeName(String trainingModeName) {
                this.trainingModeName = trainingModeName;
            }

            public Boolean getIsOperationRecommendation() {
                return isOperationRecommendation;
            }

            public void setIsOperationRecommendation(Boolean isOperationRecommendation) {
                this.isOperationRecommendation = isOperationRecommendation;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
            }

            public String getExpectSalary() {
                return expectSalary;
            }

            public void setExpectSalary(String expectSalary) {
                this.expectSalary = expectSalary;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Integer getTag() {
                return tag;
            }

            public void setTag(Integer tag) {
                this.tag = tag;
            }
        }
    }

}
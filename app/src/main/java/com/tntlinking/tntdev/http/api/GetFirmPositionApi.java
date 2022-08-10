package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//APP端职位列表(分页)
public final class GetFirmPositionApi implements IRequestApi {

    @Override
    public String getApi() {
        return "position/app/page";
    }


    private int status;// 默认传1
    private int pageNum;
    private int pageSize;

    public GetFirmPositionApi setStatus(int status) {
        this.status = status;
        return this;
    }

    public GetFirmPositionApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmPositionApi setPageSize(int pageSize) {
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
                private int companyId;
                private int recruiterId;
                private String careerDirection;
                private String title;
                private String description;
                private int recruitCount;
                private String workOper;
                private String education;
                private String trainingMode;
                private String workYears;
                private String createDate;
                private int status;
                private String workDaysMode;
                private Double startPay;
                private Double endPay;
                private Boolean industryMandatory;
                private List<String> skills;
                private int auditStatus;
                private int countRecommends;
                private int countSelfRecommends;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(int companyId) {
                    this.companyId = companyId;
                }

                public int getRecruiterId() {
                    return recruiterId;
                }

                public void setRecruiterId(int recruiterId) {
                    this.recruiterId = recruiterId;
                }

                public String getCareerDirection() {
                    return careerDirection;
                }

                public void setCareerDirection(String careerDirection) {
                    this.careerDirection = careerDirection;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getRecruitCount() {
                    return recruitCount;
                }

                public void setRecruitCount(int recruitCount) {
                    this.recruitCount = recruitCount;
                }

                public String getWorkOper() {
                    return workOper;
                }

                public void setWorkOper(String workOper) {
                    this.workOper = workOper;
                }

                public String getEducation() {
                    return education;
                }

                public void setEducation(String education) {
                    this.education = education;
                }

                public String getTrainingMode() {
                    return trainingMode;
                }

                public void setTrainingMode(String trainingMode) {
                    this.trainingMode = trainingMode;
                }

                public String getWorkYears() {
                    return workYears;
                }

                public void setWorkYears(String workYears) {
                    this.workYears = workYears;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getWorkDaysMode() {
                    return workDaysMode;
                }

                public void setWorkDaysMode(String workDaysMode) {
                    this.workDaysMode = workDaysMode;
                }

                public Double getStartPay() {
                    return startPay;
                }

                public void setStartPay(Double startPay) {
                    this.startPay = startPay;
                }

                public Double getEndPay() {
                    return endPay;
                }

                public void setEndPay(Double endPay) {
                    this.endPay = endPay;
                }

                public Boolean getIndustryMandatory() {
                    return industryMandatory;
                }

                public void setIndustryMandatory(Boolean industryMandatory) {
                    this.industryMandatory = industryMandatory;
                }

                public List<String> getSkills() {
                    return skills;
                }

                public void setSkills(List<String> skills) {
                    this.skills = skills;
                }

                public Integer getAuditStatus() {
                    return auditStatus;
                }

                public void setAuditStatus(Integer auditStatus) {
                    this.auditStatus = auditStatus;
                }

                public Integer getCountRecommends() {
                    return countRecommends;
                }

                public void setCountRecommends(Integer countRecommends) {
                    this.countRecommends = countRecommends;
                }

                public Integer getCountSelfRecommends() {
                    return countSelfRecommends;
                }

                public void setCountSelfRecommends(Integer countSelfRecommends) {
                    this.countSelfRecommends = countSelfRecommends;
                }
            }
        }

}
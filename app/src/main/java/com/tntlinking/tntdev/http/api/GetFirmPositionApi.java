package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

//APP端职位列表(分页)
public final class GetFirmPositionApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/position/app/page";
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

        public static class ListBean  implements Serializable {

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
            private double startPay;
            private double endPay;
            private Boolean industryMandatory;
            private List<String> skills;
            private String companyName;
            private String industryName;
            private int auditStatus;//1 是通过，0 是审核中 2 是未通过
            private String auditFailReason;//审核失败原因
            private int countRecommends;// 系统推荐人数
            private int countSelfRecommends;//自我推荐人数

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

            public double getStartPay() {
                return startPay;
            }

            public void setStartPay(double startPay) {
                this.startPay = startPay;
            }

            public double getEndPay() {
                return endPay;
            }

            public void setEndPay(double endPay) {
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

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public int getCountRecommends() {
                return countRecommends;
            }

            public void setCountRecommends(int countRecommends) {
                this.countRecommends = countRecommends;
            }

            public int getCountSelfRecommends() {
                return countSelfRecommends;
            }

            public void setCountSelfRecommends(int countSelfRecommends) {
                this.countSelfRecommends = countSelfRecommends;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getAuditFailReason() {
                return auditFailReason;
            }

            public void setAuditFailReason(String auditFailReason) {
                this.auditFailReason = auditFailReason;
            }

            public String getIndustryName() {
                return industryName;
            }

            public void setIndustryName(String industryName) {
                this.industryName = industryName;
            }
        }
    }

}
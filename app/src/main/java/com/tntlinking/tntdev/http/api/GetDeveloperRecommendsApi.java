package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

public final class GetDeveloperRecommendsApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer_recommends/position";
    }

    public final static class Bean implements Serializable {
        private String positionId;
        private String title;
        private String workDaysModeName;
        private int startPay;
        private int endPay;
        private String educationName;
        private String workYearsName;

        public List<String> getSkillNames() {
            return skillNames;
        }

        public void setSkillNames(List<String> skillNames) {
            this.skillNames = skillNames;
        }

        private List<String> skillNames;
        private String description;
        private String companyName;
        private String companyRecruiterRealName;
        private String companyRecruiterPosition;
        private Boolean selfRecommendStatus;

        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public int getStartPay() {
            return startPay;
        }

        public void setStartPay(int startPay) {
            this.startPay = startPay;
        }

        public int getEndPay() {
            return endPay;
        }

        public void setEndPay(int endPay) {
            this.endPay = endPay;
        }

        public String getEducationName() {
            return educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName;
        }

        public String getWorkYearsName() {
            return workYearsName;
        }

        public void setWorkYearsName(String workYearsName) {
            this.workYearsName = workYearsName;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyRecruiterRealName() {
            return companyRecruiterRealName;
        }

        public void setCompanyRecruiterRealName(String companyRecruiterRealName) {
            this.companyRecruiterRealName = companyRecruiterRealName;
        }

        public String getCompanyRecruiterPosition() {
            return companyRecruiterPosition;
        }

        public void setCompanyRecruiterPosition(String companyRecruiterPosition) {
            this.companyRecruiterPosition = companyRecruiterPosition;
        }

        public Boolean getSelfRecommendStatus() {
            return selfRecommendStatus;
        }

        public void setSelfRecommendStatus(Boolean selfRecommendStatus) {
            this.selfRecommendStatus = selfRecommendStatus;
        }


    }

}

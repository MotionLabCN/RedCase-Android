package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;

//指定时间的面试列表
public final class GetAppListDateApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/interview/appListDate";
    }
    private String interviewDate;

    public GetAppListDateApi setInterviewDate(String date) {
        this.interviewDate = date;
        return this;
    }


    public final static class Bean  {
        private String id;
        private int workDaysMode;
        private String workDaysModeName;
        private String companyName;
        private String positionName;
        private String interviewTimeType;
        private String interviewStartDate;
        private String interviewEndDate;
        private String startPay;
        private String endPay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getWorkDaysMode() {
            return workDaysMode;
        }

        public void setWorkDaysMode(int workDaysMode) {
            this.workDaysMode = workDaysMode;
        }

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getInterviewTimeType() {
            return interviewTimeType;
        }

        public void setInterviewTimeType(String interviewTimeType) {
            this.interviewTimeType = interviewTimeType;
        }

        public String getInterviewStartDate() {
            return interviewStartDate;
        }

        public void setInterviewStartDate(String interviewStartDate) {
            this.interviewStartDate = interviewStartDate;
        }

        public String getInterviewEndDate() {
            return interviewEndDate;
        }

        public void setInterviewEndDate(String interviewEndDate) {
            this.interviewEndDate = interviewEndDate;
        }

        public String getStartPay() {
            return startPay;
        }

        public void setStartPay(String startPay) {
            this.startPay = startPay;
        }

        public String getEndPay() {
            return endPay;
        }

        public void setEndPay(String endPay) {
            this.endPay = endPay;
        }
    }


}
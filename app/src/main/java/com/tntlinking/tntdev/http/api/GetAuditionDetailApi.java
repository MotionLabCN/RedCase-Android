package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//收藏开发者列表
public final class GetAuditionDetailApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/interview/appDetail";
    }

    private int interviewId;

    public GetAuditionDetailApi setInterviewId(int interviewId) {
        this.interviewId = interviewId;
        return this;
    }


    public final static class Bean {

        private String companyName;
        private Integer workDayMode;
        private String workDaysModeName;
        private Integer interviewWayId;
        private String interviewWayName;
        private String positionName;
        private String interviewStartDate;
        private String interviewEndDate;
        private String meetingCode;
        private String meetingUrl;
        private String developerMobile;
        private String developerName;
        private String recruiterMobile;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Integer getWorkDayMode() {
            return workDayMode;
        }

        public void setWorkDayMode(Integer workDayMode) {
            this.workDayMode = workDayMode;
        }

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public Integer getInterviewWayId() {
            return interviewWayId;
        }

        public void setInterviewWayId(Integer interviewWayId) {
            this.interviewWayId = interviewWayId;
        }

        public String getInterviewWayName() {
            return interviewWayName;
        }

        public void setInterviewWayName(String interviewWayName) {
            this.interviewWayName = interviewWayName;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
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

        public String getMeetingCode() {
            return meetingCode;
        }

        public void setMeetingCode(String meetingCode) {
            this.meetingCode = meetingCode;
        }

        public String getMeetingUrl() {
            return meetingUrl;
        }

        public void setMeetingUrl(String meetingUrl) {
            this.meetingUrl = meetingUrl;
        }

        public String getDeveloperMobile() {
            return developerMobile;
        }

        public void setDeveloperMobile(String developerMobile) {
            this.developerMobile = developerMobile;
        }

        public String getDeveloperName() {
            return developerName;
        }

        public void setDeveloperName(String developerName) {
            this.developerName = developerName;
        }

        public String getRecruiterMobile() {
            return recruiterMobile;
        }

        public void setRecruiterMobile(String recruiterMobile) {
            this.recruiterMobile = recruiterMobile;
        }
    }
}
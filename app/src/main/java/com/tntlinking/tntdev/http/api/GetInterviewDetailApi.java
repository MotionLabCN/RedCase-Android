package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

public final class GetInterviewDetailApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/interview/appDetail";
    }


    private String interviewId;

    public GetInterviewDetailApi setInterviewId(String interviewId) {
        this.interviewId = interviewId;
        return this;
    }

    public final static class Bean {
        private String companyName;
        private int workDayMode;
        private String workDaysModeName;
        private int interviewWayId;
        private String interviewWayName;
        private String positionName;
        private String interviewStartDate;
        private String interviewEndDate;
        private String meetingCode;
        private String meetingUrl;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getWorkDayMode() {
            return workDayMode;
        }

        public void setWorkDayMode(int workDayMode) {
            this.workDayMode = workDayMode;
        }

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public int getInterviewWayId() {
            return interviewWayId;
        }

        public void setInterviewWayId(int interviewWayId) {
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
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;

//获取企业端面试列表
public final class GetFirmInterviewListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "interview/getInterviewList";
    }



    public final static class Bean implements Serializable {
        private int developerId;
        private String interviewEndDate;
        private String interviewStartDate;
        private int positionId;
        private String realName;
        private String title;


        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getInterviewEndDate() {
            return interviewEndDate;
        }

        public void setInterviewEndDate(String interviewEndDate) {
            this.interviewEndDate = interviewEndDate;
        }

        public String getInterviewStartDate() {
            return interviewStartDate;
        }

        public void setInterviewStartDate(String interviewStartDate) {
            this.interviewStartDate = interviewStartDate;
        }

        public int getPositionId() {
            return positionId;
        }

        public void setPositionId(int positionId) {
            this.positionId = positionId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
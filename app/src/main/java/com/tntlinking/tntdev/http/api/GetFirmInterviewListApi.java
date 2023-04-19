package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取企业端面试列表
public final class GetFirmInterviewListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/interview/getInterviewList";
    }

    private int pageNum;
    private int pageSize;

    public GetFirmInterviewListApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmInterviewListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private List<ListBean> list;
        private int pageNum;
        private int total;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class ListBean {
            private int developerId;
            private int positionId;
            private int interviewId;
            private String interviewStartDate;
            private String interviewEndDate;
            private String realName;
            private String title;
            private String meetingCode;
            private String meetingUrl;
            private String dayType;

            public int getDeveloperId() {
                return developerId;
            }

            public void setDeveloperId(int developerId) {
                this.developerId = developerId;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
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

            public String getDayType() {
                return dayType;
            }

            public void setDayType(String dayType) {
                this.dayType = dayType;
            }

            public int getInterviewId() {
                return interviewId;
            }

            public void setInterviewId(int interviewId) {
                this.interviewId = interviewId;
            }
        }
    }


}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class InterviewListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "interviewSchedule/appList";
    }


    public final static class Bean {
        private int developerId;
        private String scheduleDate;
        private int status;  //1可参加/2不可参加/3部分时间可参加
        private String statusName;  //可参加/不可参加/部分时间可参加
        private boolean select;

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }


}
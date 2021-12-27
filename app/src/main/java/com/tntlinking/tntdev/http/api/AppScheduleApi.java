package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;


public final class AppScheduleApi implements IRequestApi {

    @Override
    public String getApi() {
        return "interviewSchedule/appSchedule";
    }
    private String date;

    public AppScheduleApi setData(String date) {
        this.date = date;
        return this;
    }


    public final static class Bean implements Serializable {
        private int id;
        private int developerId;
        private String title;
        private String endDate;
        private String startDate;
        private String timeFrame;
        private boolean isFullDay;
        private String scheduleDate;



        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getTimeFrame() {
            return timeFrame;
        }

        public void setTimeFrame(String timeFrame) {
            this.timeFrame = timeFrame;
        }

        public boolean isFullDay() {
            return isFullDay;
        }

        public void setFullDay(boolean fullDay) {
            isFullDay = fullDay;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}
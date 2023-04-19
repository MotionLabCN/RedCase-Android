package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateScheduleApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/interviewSchedule/update";
    }


    private int developerId;
    private int id;
    private String title;
    private Boolean isFullDay;
    private String startDate;
    private String endDate;
    private String scheduleDate;

    public UpdateScheduleApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public UpdateScheduleApi setId(int id) {
        this.id = id;
        return this;
    }

    public UpdateScheduleApi setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdateScheduleApi setFullDay(Boolean fullDay) {
        isFullDay = fullDay;
        return this;
    }

    public UpdateScheduleApi setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public UpdateScheduleApi setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public UpdateScheduleApi setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
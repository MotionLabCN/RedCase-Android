package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class AddScheduleApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "interviewSchedule/add";
    }


    private int developerId;
    private int id;
    private String title;
    private Boolean isFullDay;
    private String startDate;
    private String endDate;
    private String scheduleDate;

    public AddScheduleApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public AddScheduleApi setId(int id) {
        this.id = id;
        return this;
    }

    public AddScheduleApi setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddScheduleApi setFullDay(Boolean fullDay) {
        isFullDay = fullDay;
        return this;
    }

    public AddScheduleApi setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public AddScheduleApi setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public AddScheduleApi setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
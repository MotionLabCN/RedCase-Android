package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;



public final class DeleteScheduleApi implements IRequestApi{

    @Override
    public String getApi() {
        return "interviewSchedule/delete";
    }


    private int scheduleId;


    public DeleteScheduleApi setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }


}
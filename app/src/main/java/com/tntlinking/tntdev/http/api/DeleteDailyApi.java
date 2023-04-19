package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

public final class DeleteDailyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/orderSchedule/delete";
    }

    private int orderScheduleId;


    public DeleteDailyApi setOrderScheduleId(int orderScheduleId) {
        this.orderScheduleId = orderScheduleId;
        return this;
    }


}
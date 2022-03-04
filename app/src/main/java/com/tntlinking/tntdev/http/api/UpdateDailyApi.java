package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateDailyApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "orderSchedule/update";
    }

    private String createDate;
    private String dateOf;
    private int id;
    private String item;
    private String orderId;
    private int typeId;

    public UpdateDailyApi setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public UpdateDailyApi setDateOf(String dateOf) {
        this.dateOf = dateOf;
        return this;
    }

    public UpdateDailyApi setId(int id) {
        this.id = id;
        return this;
    }

    public UpdateDailyApi setItem(String item) {
        this.item = item;
        return this;
    }

    public UpdateDailyApi setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public UpdateDailyApi setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
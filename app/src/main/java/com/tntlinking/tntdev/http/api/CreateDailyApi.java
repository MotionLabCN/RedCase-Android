package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class CreateDailyApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/orderSchedule/create";
    }

    private String createDate;
    private String dateOf;
    private String item;
    private String orderId;
    private int typeId;

    public CreateDailyApi setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public CreateDailyApi setDateOf(String dateOf) {
        this.dateOf = dateOf;
        return this;
    }

    public CreateDailyApi setItem(String item) {
        this.item = item;
        return this;
    }

    public CreateDailyApi setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreateDailyApi setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

    public static class  Bean{
        private String orderSchedule;

        public String getOrderSchedule() {
            return orderSchedule;
        }

        public void setOrderSchedule(String orderSchedule) {
            this.orderSchedule = orderSchedule;
        }
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

//订单支付
public final class OrderPayApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/businessOrder/orderPay";
    }

    private String orders;
    private String smsCode;

    public OrderPayApi setOrders(String orders) {
        this.orders = orders;
        return this;
    }

    public OrderPayApi setSmsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }


    public static class Bean {
        private String orderSchedule;

        public String getOrderSchedule() {
            return orderSchedule;
        }

        public void setOrderSchedule(String orderSchedule) {
            this.orderSchedule = orderSchedule;
        }
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;
import com.tntlinking.tntdev.BuildConfig;

/**
 * desc   : 获取验证码
 */
public final class SendAdminSmsApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/businessOrder/sendAdminSms";
    }

    private String orderIds;

    public SendAdminSmsApi setOrderIds(String orderIds) {
        this.orderIds = orderIds;
        return this;
    }

}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


/**
 * desc   : 更换手机获取验证码
 */
public final class GetCodeChangeApi implements IRequestApi {


    @Override
    public String getApi() {
        return "sms/send/1";
    }

    /**
     * 手机号
     */
    private String mobile;

    public GetCodeChangeApi setPhone(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
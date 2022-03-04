package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 *    desc   : 获取验证码
 */
public final class GetCodeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "sms/send/verification/code";
    }

    /** 手机号 */
    private String mobile;

    public GetCodeApi setPhone(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
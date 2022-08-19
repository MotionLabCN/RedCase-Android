package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 注销企业端
 */
public final class CancelFirmApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/member/cancellation";
    }

    private String mobile;

    public CancelFirmApi setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
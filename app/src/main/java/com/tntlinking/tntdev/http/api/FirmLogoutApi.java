package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 企业端退出登录
 */
public final class FirmLogoutApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/member/logout";
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 *
 *    desc   : 退出登录
 */
public final class LogoutApi implements IRequestApi {

    @Override
    public String getApi() {
        return "system/logout";
    }
}
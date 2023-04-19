package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 *
 *    desc   : 获取用户信息
 */
public final class UserInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "user/info";
    }

    public final class Bean {

    }
}
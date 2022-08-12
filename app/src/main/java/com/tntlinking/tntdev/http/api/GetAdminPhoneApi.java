package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 获取企业管理员电话号码
 */
public final class GetAdminPhoneApi implements IRequestApi {

    @Override
    public String getApi() {
        return "businessOrder/getAdminPhone";
    }

}
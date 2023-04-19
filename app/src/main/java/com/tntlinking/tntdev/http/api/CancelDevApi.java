package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 注销开发者
 */
public final class CancelDevApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/cancellation";
    }


}
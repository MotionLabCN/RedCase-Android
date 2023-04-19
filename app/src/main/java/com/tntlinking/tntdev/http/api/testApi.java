package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;

import androidx.annotation.NonNull;

/**
 */
public final class testApi implements IRequestServer, IRequestApi {

    @NonNull
    @Override
    public String getHost() {
        return "https://www.baidu.com/";
    }

    @NonNull
    @Override
    public String getApi() {
        return "user/getInfo";
    }
}
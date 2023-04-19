package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//获取是否有未读消息
public final class GetTaskStatusApi implements IRequestApi {

    @Override
    public String getApi() {
        return "task/publicity_switch";
    }


}
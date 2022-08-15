package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//取消面试
public final class CancelInterviewApi implements IRequestApi {

    @Override
    public String getApi() {
        return "interview/cancel";
    }

    private int interviewId;
    private int pageSize;

    public CancelInterviewApi setInterviewId(int interviewId) {
        this.interviewId = interviewId;
        return this;
    }


}
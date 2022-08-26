package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


//判断是否为管理员
public final class GetJudgeAdminApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/company/judgeAdmin";
    }


}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

import java.io.File;

/**
 * desc   : 上传图片
 */
public final class ParseAnalysisApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/parse_resume/" + fileName;
    }


    @HttpIgnore
    private String fileName;

    public ParseAnalysisApi setFile(String fileName) {
        this.fileName = fileName;
        return this;
    }


}
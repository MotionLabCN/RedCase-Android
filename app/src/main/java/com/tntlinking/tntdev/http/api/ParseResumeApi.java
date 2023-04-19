package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import org.aspectj.lang.annotation.RequiredTypes;

import java.io.File;

import okhttp3.MultipartBody;

/**
 *
 *    desc   : 上传图片
 */
public final class ParseResumeApi implements IRequestApi{

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/upload_resume";
    }

    /** 图片文件 */
    private File file;

    public ParseResumeApi setFile(File file) {
        this.file = file;
        return this;
    }

    public final static class Bean {
        private  String fileName;
        private  String url;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
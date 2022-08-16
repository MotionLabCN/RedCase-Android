package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.File;

/**
 *
 *    desc   : 上传图片
 */
public final class UpdateImageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/update/image";
    }

    /** 图片文件 */
    private File image;

    public UpdateImageApi setImage(File image) {
        this.image = image;
        return this;
    }
}
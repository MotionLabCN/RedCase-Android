package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.File;

/**
 *
 *    desc   : 上传图片
 */
public final class UpdateAvatarApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer/modify/avatar";
    }

    /** 图片文件 */
    private File files;

    public UpdateAvatarApi setFiles(File files) {
        this.files = files;
        return this;
    }
}
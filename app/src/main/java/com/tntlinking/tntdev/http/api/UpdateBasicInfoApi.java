package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateBasicInfoApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/update/basic/info";
    }


    private int developerId;
    private String realName;
    private int sex;
    private String birthday;
    private int provinceId;
    private int cityId;
    private int areasId;
    private int remoteWorkReason;

    public UpdateBasicInfoApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public UpdateBasicInfoApi setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public UpdateBasicInfoApi setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public UpdateBasicInfoApi setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public UpdateBasicInfoApi setAreasId(int areasId) {
        this.areasId = areasId;
        return this;
    }

    public UpdateBasicInfoApi setRemoteWorkReason(int remoteWorkReason) {
        this.remoteWorkReason = remoteWorkReason;
        return this;
    }

    public UpdateBasicInfoApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public UpdateBasicInfoApi setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}
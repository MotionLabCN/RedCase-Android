package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 * 企业认证更换操作
 */
public final class FirmCertificationChangeApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/change";
    }

    private String address = "";
    private int provinceId = 1;// 省
    private int cityId = 1;// 城市
    private int areaId = 1;// 区
    private int companyId = 1;//
    private String email;
    private String emailCode;
    private String positionName;
    private String realName;


    public FirmCertificationChangeApi setAddress(String address) {
        this.address = address;
        return this;
    }

    public FirmCertificationChangeApi setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public FirmCertificationChangeApi setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public FirmCertificationChangeApi setAreaId(int areasId) {
        this.areaId = areasId;
        return this;
    }

    public FirmCertificationChangeApi setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public FirmCertificationChangeApi setEmail(String email) {
        this.email = email;
        return this;
    }

    public FirmCertificationChangeApi setEmailCode(String emailCode) {
        this.emailCode = emailCode;
        return this;
    }

    public FirmCertificationChangeApi setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public FirmCertificationChangeApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
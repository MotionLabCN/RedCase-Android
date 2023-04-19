package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;

/**
 * 企业认证审核
 */
public final class FirmCertificationApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/upgradeAccount";
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


    public FirmCertificationApi setAddress(String address) {
        this.address = address;
        return this;
    }

    public FirmCertificationApi setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public FirmCertificationApi setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public FirmCertificationApi setAreaId(int areasId) {
        this.areaId = areasId;
        return this;
    }

    public FirmCertificationApi setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public FirmCertificationApi setEmail(String email) {
        this.email = email;
        return this;
    }

    public FirmCertificationApi setEmailCode(String emailCode) {
        this.emailCode = emailCode;
        return this;
    }

    public FirmCertificationApi setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public FirmCertificationApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
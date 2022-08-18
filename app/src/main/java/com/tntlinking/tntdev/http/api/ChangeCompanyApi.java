package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;

//更换公司
public final class ChangeCompanyApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/change";
    }


    private String address;   //地址
    private int provinceId;    //省编码
    private int cityId;//市编码
    private int areaId;//	区编码
    private String companyId;    //公司id
    private String email;    //邮箱
    private String emailCode;    //邮箱验证码
    private String positionName;    //职位
    private String realName;    //真实姓名

    public ChangeCompanyApi setAddress(String address) {
        this.address = address;
        return this;
    }

    public ChangeCompanyApi setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public ChangeCompanyApi setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public ChangeCompanyApi setAreaId(int areaId) {
        this.areaId = areaId;
        return this;
    }

    public ChangeCompanyApi setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public ChangeCompanyApi setEmail(String email) {
        this.email = email;
        return this;
    }

    public ChangeCompanyApi setEmailCode(String emailCode) {
        this.emailCode = emailCode;
        return this;
    }

    public ChangeCompanyApi setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public ChangeCompanyApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
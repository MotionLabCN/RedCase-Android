package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 * 升级企业账户
 */
public final class CreateNewCompanyApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/create";
    }

    private String businessLicense;
    private String companyName;
    private String emailSuffix;
    private int industryId;
    private int personSizeId;
    private String shortName;
    private String taxInvoice;


    public CreateNewCompanyApi setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
        return this;
    }

    public CreateNewCompanyApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public CreateNewCompanyApi setEmailSuffix(String emailSuffix) {
        this.emailSuffix = emailSuffix;
        return this;
    }

    public CreateNewCompanyApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }

    public CreateNewCompanyApi setPersonSizeId(int personSizeId) {
        this.personSizeId = personSizeId;
        return this;
    }

    public CreateNewCompanyApi setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public CreateNewCompanyApi setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
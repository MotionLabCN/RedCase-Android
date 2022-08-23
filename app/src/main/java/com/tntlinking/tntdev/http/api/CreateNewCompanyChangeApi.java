package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 * 新建企业(给更换企业使用)
 */
public final class CreateNewCompanyChangeApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/create/offerChangeCompany";
    }

    private String businessLicense;
    private String companyName;
    private String emailSuffix;
    private int industryId;
    private int personSizeId;
    private String shortName;
    private String taxInvoice;


    public CreateNewCompanyChangeApi setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
        return this;
    }

    public CreateNewCompanyChangeApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public CreateNewCompanyChangeApi setEmailSuffix(String emailSuffix) {
        this.emailSuffix = emailSuffix;
        return this;
    }

    public CreateNewCompanyChangeApi setIndustryId(int industryId) {
        this.industryId = industryId;
        return this;
    }

    public CreateNewCompanyChangeApi setPersonSizeId(int personSizeId) {
        this.personSizeId = personSizeId;
        return this;
    }

    public CreateNewCompanyChangeApi setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public CreateNewCompanyChangeApi setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


//获取公司详细信息
public final class GetCompanyInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/company/info";
    }

    public final static class Bean {

        private int id;
        private String companyName;
        private String shortName;
        private String industry;
        private String taxInvoice;
        private String personSize;
        private String emailSuffix;
        private String officialWebsite;
        private String address;
        private String foundDate;
        private String registeredFund;
        private String profile;
        private String businessLicense;
        private int industryId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getTaxInvoice() {
            return taxInvoice;
        }

        public void setTaxInvoice(String taxInvoice) {
            this.taxInvoice = taxInvoice;
        }

        public String getPersonSize() {
            return personSize;
        }

        public void setPersonSize(String personSize) {
            this.personSize = personSize;
        }

        public String getEmailSuffix() {
            return emailSuffix;
        }

        public void setEmailSuffix(String emailSuffix) {
            this.emailSuffix = emailSuffix;
        }

        public String getOfficialWebsite() {
            return officialWebsite;
        }

        public void setOfficialWebsite(String officialWebsite) {
            this.officialWebsite = officialWebsite;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFoundDate() {
            return foundDate;
        }

        public void setFoundDate(String foundDate) {
            this.foundDate = foundDate;
        }

        public String getRegisteredFund() {
            return registeredFund;
        }

        public void setRegisteredFund(String registeredFund) {
            this.registeredFund = registeredFund;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public Integer getIndustryId() {
            return industryId;
        }

        public void setIndustryId(Integer industryId) {
            this.industryId = industryId;
        }
    }
}
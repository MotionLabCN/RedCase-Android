package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//搜索公司
public final class SearchCompanyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/company/searchCompany";
    }


    private String name;


    public SearchCompanyApi setName(String name) {
        this.name = name;
        return this;
    }


    public final static class Bean {

        private int id;
        private String companyName;
        private String shortName;
        private int industryId;
        private String taxInvoice;
        private int personSizeId;
        private String teamToolsDescId;
        private String createDate;
        private int status;
        private String lastModifyDate;
        private String createUser;
        private String lastModifyUser;
        private String businessLicense;
        private String emailSuffix;
        private String officialWebsite;
        private String address;
        private String registeredFund;
        private String foundDate;
        private String profile;

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

        public int getIndustryId() {
            return industryId;
        }

        public void setIndustryId(int industryId) {
            this.industryId = industryId;
        }

        public String getTaxInvoice() {
            return taxInvoice;
        }

        public void setTaxInvoice(String taxInvoice) {
            this.taxInvoice = taxInvoice;
        }

        public int getPersonSizeId() {
            return personSizeId;
        }

        public void setPersonSizeId(int personSizeId) {
            this.personSizeId = personSizeId;
        }

        public String getTeamToolsDescId() {
            return teamToolsDescId;
        }

        public void setTeamToolsDescId(String teamToolsDescId) {
            this.teamToolsDescId = teamToolsDescId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLastModifyDate() {
            return lastModifyDate;
        }

        public void setLastModifyDate(String lastModifyDate) {
            this.lastModifyDate = lastModifyDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getLastModifyUser() {
            return lastModifyUser;
        }

        public void setLastModifyUser(String lastModifyUser) {
            this.lastModifyUser = lastModifyUser;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
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

        public String getRegisteredFund() {
            return registeredFund;
        }

        public void setRegisteredFund(String registeredFund) {
            this.registeredFund = registeredFund;
        }

        public Object getFoundDate() {
            return foundDate;
        }

        public void setFoundDate(String foundDate) {
            this.foundDate = foundDate;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }

}
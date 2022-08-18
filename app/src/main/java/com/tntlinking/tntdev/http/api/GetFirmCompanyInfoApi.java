package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 获取公司详细信息
 */
public final class GetFirmCompanyInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/companyInfo";
    }

    public static class Bean {
        private String companyName;
        private String id;
        private String industry;
        private String personSize;
        private String shortName;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getPersonSize() {
            return personSize;
        }

        public void setPersonSize(String personSize) {
            this.personSize = personSize;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }

}
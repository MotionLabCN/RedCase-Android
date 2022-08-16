package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class GetSignParamApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/signContract/getSignParam";
    }


    public class Bean{
        private String bankCardId;
        private String bankName;
        private int developerId;
        private int distributionChannel;
        private String idNumber;
        private String mobile;
        private String realName;

        public String getBankCardId() {
            return bankCardId;
        }

        public void setBankCardId(String bankCardId) {
            this.bankCardId = bankCardId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public int getDistributionChannel() {
            return distributionChannel;
        }

        public void setDistributionChannel(int distributionChannel) {
            this.distributionChannel = distributionChannel;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}
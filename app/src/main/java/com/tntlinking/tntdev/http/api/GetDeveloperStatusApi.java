package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class GetDeveloperStatusApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer/info";
    }


    public final static class Bean {
        private int id;
        private String mobile;
        private String nickName;
        private String realName;
        private String createDate;
        private String status;
        private String channel;
        private String typeId;
        private String businessStatus;
        private String sex;
        private String birthday;
        private int provinceId;
        private int cityId;
        private int areasId;
        private String avatarUrl;
        private String lastModifyDate;
        private String remoteWorkReason;
        private String careerDirectionId;
        private String careerDirection;
        private String signContractNum;
        private String profitTotal;
        private int contractStatus;//签约状态 0, "待签约"，1, "签约中" 2, "签约成功" 3, "签约失败"


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getAreasId() {
            return areasId;
        }

        public void setAreasId(int areasId) {
            this.areasId = areasId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getLastModifyDate() {
            return lastModifyDate;
        }

        public void setLastModifyDate(String lastModifyDate) {
            this.lastModifyDate = lastModifyDate;
        }

        public String getRemoteWorkReason() {
            return remoteWorkReason;
        }

        public void setRemoteWorkReason(String remoteWorkReason) {
            this.remoteWorkReason = remoteWorkReason;
        }

        public String getCareerDirectionId() {
            return careerDirectionId;
        }

        public void setCareerDirectionId(String careerDirectionId) {
            this.careerDirectionId = careerDirectionId;
        }

        public String getCareerDirection() {
            return careerDirection;
        }

        public void setCareerDirection(String careerDirection) {
            this.careerDirection = careerDirection;
        }

        public String getSignContractNum() {
            return signContractNum;
        }

        public void setSignContractNum(String signContractNum) {
            this.signContractNum = signContractNum;
        }

        public String getProfitTotal() {
            return profitTotal;
        }

        public void setProfitTotal(String profitTotal) {
            this.profitTotal = profitTotal;
        }

        public String getStatus() {
            return status;
        }

        public int getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(int contractStatus) {
            this.contractStatus = contractStatus;
        }
    }
}
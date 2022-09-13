package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


//获取企业相关信息
public final class GetFirmInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/companyRecruiterAccount/getInfo";
    }


    public final static class Bean {
        private int id;
        private String mobile;
        private String realName;
        private String position;
        private String avatarUrl;
        private int status;
        private int typeId;//0 未加入 1管理员 2 子账号

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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }
}
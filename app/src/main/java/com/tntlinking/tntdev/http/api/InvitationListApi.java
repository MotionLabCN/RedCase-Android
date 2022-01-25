package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class InvitationListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer/invitation/list";
    }


    public final static class Bean {
        private String developerName;
        private int developerStatus;
        private String createDate;

        public String getDeveloperName() {
            return developerName;
        }

        public void setDeveloperName(String developerName) {
            this.developerName = developerName;
        }

        public int getDeveloperStatus() {
            return developerStatus;
        }

        public void setDeveloperStatus(int developerStatus) {
            this.developerStatus = developerStatus;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
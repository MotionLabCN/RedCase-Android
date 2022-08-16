package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class InvitationUrlApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/invitation/url";
    }

    public final static class Bean {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
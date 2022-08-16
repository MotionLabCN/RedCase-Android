package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class GetAppUpdateApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/appversion/latest";
    }


    private int osType;
    private String currVersion ;

    public GetAppUpdateApi setOsType(int id) {
        this.osType = id;
        return this;
    }
    public GetAppUpdateApi setCurrVersion(String version) {
        this.currVersion = version;
        return this;
    }


    public final static class Bean {
        private int id;
        private String version;
        private String lowestRequireVersion;
        private int forceUpdate;
        private String downloadUrl;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLowestRequireVersion() {
            return lowestRequireVersion;
        }

        public void setLowestRequireVersion(String lowestRequireVersion) {
            this.lowestRequireVersion = lowestRequireVersion;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
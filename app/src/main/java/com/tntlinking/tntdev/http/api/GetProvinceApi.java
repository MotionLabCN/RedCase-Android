package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;


public final class GetProvinceApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/dictionary/region/tree";
    }


    public final static class ProvinceBean {
        private int id;
        private String regionName;
        private String regionShortName;
        private int regionCode;
        private int regionLevel;
        private String parentId;
        private List<AreaBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionShortName() {
            return regionShortName;
        }

        public void setRegionShortName(String regionShortName) {
            this.regionShortName = regionShortName;
        }

        public int getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(int regionCode) {
            this.regionCode = regionCode;
        }

        public int getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(int regionLevel) {
            this.regionLevel = regionLevel;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public List<AreaBean> getChildren() {
            return children;
        }

        public void setChildren(List<AreaBean> children) {
            this.children = children;
        }
    }

    public static class AreaBean {
        private int id;
        private String regionName;
        private String regionShortName;
        private int regionCode;
        private int regionLevel;
        private int parentId;
        private List<CityBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionShortName() {
            return regionShortName;
        }

        public void setRegionShortName(String regionShortName) {
            this.regionShortName = regionShortName;
        }

        public int getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(int regionCode) {
            this.regionCode = regionCode;
        }

        public int getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(int regionLevel) {
            this.regionLevel = regionLevel;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public List<CityBean> getChildren() {
            return children;
        }

        public void setChildren(List<CityBean> children) {
            this.children = children;
        }
    }

    public static class CityBean {
        private int id;
        private String regionName;
        private String regionShortName;
        private String regionCode;
        private int parentId;
        private int regionLevel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionShortName() {
            return regionShortName;
        }

        public void setRegionShortName(String regionShortName) {
            this.regionShortName = regionShortName;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(int regionLevel) {
            this.regionLevel = regionLevel;
        }
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class GetDictionaryApi implements IRequestApi {

    @Override
    public String getApi() {
        return "dictionary/getByParentId/"+parentId;
    }

    @HttpIgnore
    private String parentId;

    public GetDictionaryApi setParentId(String id) {
        this.parentId = id;
        return this;
    }

    public final static class DictionaryBean {
        private int id;
        private String name;
        private String parentId;
        private String status;


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getParentId() {
            return parentId;
        }

        public String getStatus() {
            return status;
        }
    }
}
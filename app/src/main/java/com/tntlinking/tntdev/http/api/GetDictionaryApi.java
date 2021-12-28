package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

import java.util.List;


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
        public List<ChildrenBean> children;

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

        public void setId(int id) {
            this.id = id;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }


    }

    public  class ChildrenBean {
        private int id;
        private String name;
        private String parentId;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
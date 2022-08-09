package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//企业端获取职业方向
public final class GetCareerDirectionApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developerReveal/getCareerDirection";
    }


    public static class Bean {

        private Integer id;
        private String name;
        private Integer parentId;
        private Integer status;
        private Object typeId;
        private Object children;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getTypeId() {
            return typeId;
        }

        public void setTypeId(Object typeId) {
            this.typeId = typeId;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }
    }
}
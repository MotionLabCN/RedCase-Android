package com.tntlinking.tntdev.http.api;
import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;


public final class GetTagListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/skill/tree/developer";
    }

    public String careerDirectionId;

    public GetTagListApi setCareerDirectionId(String careerDirectionId) {
        this.careerDirectionId = careerDirectionId;
        return this;
    }

    public final static class Bean {
        private String id;
        private String skillName;
        private String parentId;
        private String createDate;
        private String typeId;
        private List<ChildrenBean> children;

        public static class ChildrenBean implements Serializable {
            private int id;
            private String skillName;
            private String parentId;
            private String createDate;
            private String typeId;
            private String children;
            private int type =0;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSkillName() {
                return skillName;
            }

            public void setSkillName(String skillName) {
                this.skillName = skillName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getTypeId() {
                return typeId;
            }

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public String getChildren() {
                return children;
            }

            public void setChildren(String children) {
                this.children = children;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public String getId() {
            return id;
        }

        public String getSkillName() {
            return skillName;
        }

        public String getParentId() {
            return parentId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public String getTypeId() {
            return typeId;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }
    }
}
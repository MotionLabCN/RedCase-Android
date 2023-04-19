package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取消息列表
public final class GetFirmMessageListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/JPushCompanyRecruiter/getMessageList";
    }

    private int pageNum;
    private int pageSize = 20;


    public GetFirmMessageListApi setPageNum(int num) {
        this.pageNum = num;
        return this;
    }

    public GetFirmMessageListApi setPageSize(int size) {
        this.pageSize = size;
        return this;
    }

    public final static class Bean {
        private Integer total;
        private Integer pageNum;
        private List<ListBean> list;

        public static class ListBean {
            private int companyRecruiterId;
            private int id;
            private int isRead;
            private int messageType;
            private int typeId;
            private String messageStr;
            private String createTime;
            private String sendTime;

            public int getCompanyRecruiterId() {
                return companyRecruiterId;
            }

            public void setCompanyRecruiterId(int companyRecruiterId) {
                this.companyRecruiterId = companyRecruiterId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public int getMessageType() {
                return messageType;
            }

            public void setMessageType(int messageType) {
                this.messageType = messageType;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public String getMessageStr() {
                return messageStr;
            }

            public void setMessageStr(String messageStr) {
                this.messageStr = messageStr;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPageNum() {
            return pageNum;
        }

        public void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取消息列表
public final class GetMessageListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/jpush/getMessageList";
    }

    private int pageNum;
    private int pageSize;


    public GetMessageListApi setPageNum(int num) {
        this.pageNum = num;
        return this;
    }

    public GetMessageListApi setPageSize(int size) {
        this.pageSize = size;
        return this;
    }

    public final static class Bean {
        private Integer total;
        private Integer pageNum;
        private List<ListBean> list;

        public static class ListBean {
            private int id;
            private int developerId;
            private int messageType; //消息业务类型 0：职位上新 1：职位推荐 2：面试邀约 3：面试提醒 4:面试开始前提醒
            private int typeId;
            private String messageStr;
            private String createTime;
            private String sendTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDeveloperId() {
                return developerId;
            }

            public void setDeveloperId(int developerId) {
                this.developerId = developerId;
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
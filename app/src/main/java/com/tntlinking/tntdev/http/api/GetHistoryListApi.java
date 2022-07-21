package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取历史日报列表
public final class GetHistoryListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "orderSchedule/historyList";
    }

    private String orderId;
    private int pageNum;
    private int pageSize;

    public GetHistoryListApi setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public GetHistoryListApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetHistoryListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {

        private List<CommonBean> done;
        private List<CommonBean> running;
        private List<CommonBean> future;
        private List<CommonBean> help;
        private Integer count;
        private String dateOf;

        public List<CommonBean> getDone() {
            return done;
        }

        public void setDone(List<CommonBean> done) {
            this.done = done;
        }

        public List<CommonBean> getRunning() {
            return running;
        }

        public void setRunning(List<CommonBean> running) {
            this.running = running;
        }

        public List<CommonBean> getFuture() {
            return future;
        }

        public void setFuture(List<CommonBean> future) {
            this.future = future;
        }

        public List<CommonBean> getHelp() {
            return help;
        }

        public void setHelp(List<CommonBean> help) {
            this.help = help;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getDateOf() {
            return dateOf;
        }

        public void setDateOf(String dateOf) {
            this.dateOf = dateOf;
        }

        public static class CommonBean {
            private Integer id;
            private String dateOf;
            private String item;
            private Integer typeId;
            private Integer orderId;
            private String createDate;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getDateOf() {
                return dateOf;
            }

            public void setDateOf(String dateOf) {
                this.dateOf = dateOf;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public Integer getTypeId() {
                return typeId;
            }

            public void setTypeId(Integer typeId) {
                this.typeId = typeId;
            }

            public Integer getOrderId() {
                return orderId;
            }

            public void setOrderId(Integer orderId) {
                this.orderId = orderId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }


    }

}
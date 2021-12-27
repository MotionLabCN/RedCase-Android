package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

public final class GetDailyListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "orderSchedule/list";
    }

    private String orderId;


    public GetDailyListApi setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public final static class Bean {
        private List<ListBean> done;
        private List<ListBean> running;
        private List<ListBean> future;
        private List<ListBean> help;
        private int count;

        public List<ListBean> getDone() {
            return done;
        }

        public void setDone(List<ListBean> done) {
            this.done = done;
        }

        public List<ListBean> getRunning() {
            return running;
        }

        public void setRunning(List<ListBean> running) {
            this.running = running;
        }

        public List<ListBean> getFuture() {
            return future;
        }

        public void setFuture(List<ListBean> future) {
            this.future = future;
        }

        public List<ListBean> getHelp() {
            return help;
        }

        public void setHelp(List<ListBean> help) {
            this.help = help;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class ListBean {
        private int id;
        private String dateOf;
        private String item;
        private int typeId;
        private String orderId;
        private String createDate;
        private int type = 5;

        public ListBean() {
        }

        public ListBean(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
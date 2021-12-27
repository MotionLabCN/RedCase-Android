package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;


public final class developerBillListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developerBill/list";
    }


    private int pageNum;
    private int pageSize = 20;

    public developerBillListApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public developerBillListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private String total;
        private String pageNum;
        private List<ListBean> list;

        public final static class ListBean {
            private String id;
            private String orderId;
            private String developerId;
            private String serviceMoney;
            private String deductMoney;
            private String personalTax;
            private String actualMoney;
            private String createDate;
            private String status;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getDeveloperId() {
                return developerId;
            }

            public void setDeveloperId(String developerId) {
                this.developerId = developerId;
            }

            public String getServiceMoney() {
                return serviceMoney;
            }

            public void setServiceMoney(String serviceMoney) {
                this.serviceMoney = serviceMoney;
            }

            public String getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(String deductMoney) {
                this.deductMoney = deductMoney;
            }

            public String getPersonalTax() {
                return personalTax;
            }

            public void setPersonalTax(String personalTax) {
                this.personalTax = personalTax;
            }

            public String getActualMoney() {
                return actualMoney;
            }

            public void setActualMoney(String actualMoney) {
                this.actualMoney = actualMoney;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
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
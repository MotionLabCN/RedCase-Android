package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;


public final class developerBillDetailApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developerBill/detail";
    }


    private String developerBillId;


    public developerBillDetailApi setDeveloperBillId(String developerBillId) {
        this.developerBillId = developerBillId;
        return this;
    }

    public final static class Bean {
        private String timeStart;
        private String timeEnd;
        private String createDate;
        private String personalTax;
        private String actualMoney;
        private String developerId;
        private String orderCounts;
        private String status;
        private List<ListBean> orders;


        public final static class ListBean {
            private String id;
            private String orderNo;
            private String workStartDate;
            private String finishDate;
            private String createDate;
            private String totalAmount;
            private double refundMoney;
            private String actualMoney;
            private String refundReason;
            private String days;
            private String orderStatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getWorkStartDate() {
                return workStartDate;
            }

            public void setWorkStartDate(String workStartDate) {
                this.workStartDate = workStartDate;
            }

            public String getFinishDate() {
                return finishDate;
            }

            public void setFinishDate(String finishDate) {
                this.finishDate = finishDate;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public double getRefundMoney() {
                return refundMoney;
            }

            public void setRefundMoney(double refundMoney) {
                this.refundMoney = refundMoney;
            }

            public String getActualMoney() {
                return actualMoney;
            }

            public void setActualMoney(String actualMoney) {
                this.actualMoney = actualMoney;
            }

            public String getRefundReason() {
                return refundReason;
            }

            public void setRefundReason(String refundReason) {
                this.refundReason = refundReason;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public String getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(String developerId) {
            this.developerId = developerId;
        }

        public String getOrderCounts() {
            return orderCounts;
        }

        public void setOrderCounts(String orderCounts) {
            this.orderCounts = orderCounts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ListBean> getOrders() {
            return orders;
        }

        public void setOrders(List<ListBean> orders) {
            this.orders = orders;
        }
    }
}
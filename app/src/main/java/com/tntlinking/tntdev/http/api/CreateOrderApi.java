package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.io.Serializable;
import java.util.List;


public final class CreateOrderApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "businessOrder/createOrder";
    }


    private int developerId;   //
    private int positionId;   //
    private String workStartDate;//


    public CreateOrderApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public CreateOrderApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public CreateOrderApi setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
        return this;
    }

    public final static class Bean implements Serializable {

        private String orderIds;
        private Integer time;
        private PayInfoBean payInfo;

        public String getOrderIds() {
            return orderIds;
        }

        public void setOrderIds(String orderIds) {
            this.orderIds = orderIds;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public PayInfoBean getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(PayInfoBean payInfo) {
            this.payInfo = payInfo;
        }

        public static class PayInfoBean implements Serializable {
            private double totalAmount;
            private double serviceAmount;
            private double freezeAmount;
            private List<PreOrderListBean> preOrderList;

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public double getServiceAmount() {
                return serviceAmount;
            }

            public void setServiceAmount(double serviceAmount) {
                this.serviceAmount = serviceAmount;
            }

            public double getFreezeAmount() {
                return freezeAmount;
            }

            public void setFreezeAmount(double freezeAmount) {
                this.freezeAmount = freezeAmount;
            }

            public List<PreOrderListBean> getPreOrderList() {
                return preOrderList;
            }

            public void setPreOrderList(List<PreOrderListBean> preOrderList) {
                this.preOrderList = preOrderList;
            }

            public static class PreOrderListBean implements Serializable {
                private double totalAmount;
                private int workDaysMode;
                private int preDays;
                private String workStartDate;
                private String workEndDate;
                private double serviceMoney;

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public int getWorkDaysMode() {
                    return workDaysMode;
                }

                public void setWorkDaysMode(int workDaysMode) {
                    this.workDaysMode = workDaysMode;
                }

                public int getPreDays() {
                    return preDays;
                }

                public void setPreDays(int preDays) {
                    this.preDays = preDays;
                }

                public String getWorkStartDate() {
                    return workStartDate;
                }

                public void setWorkStartDate(String workStartDate) {
                    this.workStartDate = workStartDate;
                }

                public String getWorkEndDate() {
                    return workEndDate;
                }

                public void setWorkEndDate(String workEndDate) {
                    this.workEndDate = workEndDate;
                }

                public double getServiceMoney() {
                    return serviceMoney;
                }

                public void setServiceMoney(double serviceMoney) {
                    this.serviceMoney = serviceMoney;
                }
            }
        }
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
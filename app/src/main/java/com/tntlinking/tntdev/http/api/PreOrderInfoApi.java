package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;

// 获取订单详情
public final class PreOrderInfoApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "businessOrder/preOrderInfo";
    }

    private int orderId;   //


    public PreOrderInfoApi setOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }


    public final static class Bean {
        private int orderId;
        private int orderStatus;
        private double totalAmount;
        private double serviceAmount;
        private double freezeAmount;
        private List<PreOrderListBean> preOrderList;
        private String avatarUrl;
        private int developerId;
        private String realName;
        private String careerDirectionName;
        private int positionId;
        private String workDayModeName;
        private double expectSalary;
        private int time;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

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

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCareerDirectionName() {
            return careerDirectionName;
        }

        public void setCareerDirectionName(String careerDirectionName) {
            this.careerDirectionName = careerDirectionName;
        }

        public int getPositionId() {
            return positionId;
        }

        public void setPositionId(int positionId) {
            this.positionId = positionId;
        }

        public String getWorkDayModeName() {
            return workDayModeName;
        }

        public void setWorkDayModeName(String workDayModeName) {
            this.workDayModeName = workDayModeName;
        }

        public double getExpectSalary() {
            return expectSalary;
        }

        public void setExpectSalary(double expectSalary) {
            this.expectSalary = expectSalary;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public static class PreOrderListBean {
            private double totalAmount;
            private int workDaysMode;
            private double preDays;
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

            public double getPreDays() {
                return preDays;
            }

            public void setPreDays(double preDays) {
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

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;


public final class PostCalculateApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "manpower-rest-api/businessOrder/pre/calculate";
    }


    private int developerId;   //
    private int positionId;   //
    private String workStartDate;//


    public PostCalculateApi setDeveloperId(int developerId) {
        this.developerId = developerId;
        return this;
    }

    public PostCalculateApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public PostCalculateApi setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
        return this;
    }
    public final static class Bean {

        private double totalAmount;
        private double serviceAmount;
        private double freezeAmount;
        private List<PreListBean> preList;

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

        public List<PreListBean> getPreList() {
            return preList;
        }

        public void setPreList(List<PreListBean> preList) {
            this.preList = preList;
        }

        public static class PreListBean {
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

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
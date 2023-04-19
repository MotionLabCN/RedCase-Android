package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

//邀约面试列表
public final class AppListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/order/appList";
    }

    private int orderStatus; //2 待服务，3 服务中

    public AppListApi setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public final static class Bean {
        private String id;
        private String orderStatus;
        private String orderNo;
        private String workDaysMode;
        private String workDaysModeName;
        private String companyName;
        private String positionName;
        private String statusName;
        private String serviceName;
        private String finishDate;
        private String workStartDate;
        private int type;

        private String interviewTimeType;
        private String interviewStartDate;
        private String interviewEndDate;
        private String startPay;
        private String endPay;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getWorkDaysMode() {
            return workDaysMode;
        }

        public void setWorkDaysMode(String workDaysMode) {
            this.workDaysMode = workDaysMode;
        }

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getWorkStartDate() {
            return workStartDate;
        }

        public void setWorkStartDate(String workStartDate) {
            this.workStartDate = workStartDate;
        }

        public String getInterviewTimeType() {
            return interviewTimeType;
        }

        public void setInterviewTimeType(String interviewTimeType) {
            this.interviewTimeType = interviewTimeType;
        }

        public String getInterviewStartDate() {
            return interviewStartDate;
        }

        public void setInterviewStartDate(String interviewStartDate) {
            this.interviewStartDate = interviewStartDate;
        }

        public String getInterviewEndDate() {
            return interviewEndDate;
        }

        public void setInterviewEndDate(String interviewEndDate) {
            this.interviewEndDate = interviewEndDate;
        }

        public String getStartPay() {
            return startPay;
        }

        public void setStartPay(String startPay) {
            this.startPay = startPay;
        }

        public String getEndPay() {
            return endPay;
        }

        public void setEndPay(String endPay) {
            this.endPay = endPay;
        }
    }
}
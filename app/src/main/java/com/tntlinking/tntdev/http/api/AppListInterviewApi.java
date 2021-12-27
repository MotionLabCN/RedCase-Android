package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class AppListInterviewApi implements IRequestApi {

    @Override
    public String getApi() {
        return "interview/appList";
    }


    public final static class Bean {
        private String id;
        private String orderStatus;
        private String workDaysMode;
        private String workDaysModeName;
        private String companyName;
        private String positionName;
        private String statusName;
        private String serviceName;
        private String finishDate;
        private String workStartDate;
        private int type;

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
    }
}
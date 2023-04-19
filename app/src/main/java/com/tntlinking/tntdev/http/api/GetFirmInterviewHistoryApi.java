package com.tntlinking.tntdev.http.api;
import com.hjq.http.config.IRequestApi;


import java.util.List;

//企业端获取历史面试者列表
public final class GetFirmInterviewHistoryApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/interview/getInterviewHistory";
    }

    private int pageNum;
    private int pageSize;

    public GetFirmInterviewHistoryApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmInterviewHistoryApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private List<ListBean> list;
        private int pageNum;
        private int total;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class ListBean {

            private int id;
            private Object orderId;
            private int developerId;
            private double serviceMoney;
            private double deductMoney;
            private double personalTax;
            private double actualMoney;
            private String createDate;
            private int status;
            private String grantDate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public int getDeveloperId() {
                return developerId;
            }

            public void setDeveloperId(int developerId) {
                this.developerId = developerId;
            }

            public double getServiceMoney() {
                return serviceMoney;
            }

            public void setServiceMoney(double serviceMoney) {
                this.serviceMoney = serviceMoney;
            }

            public double getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(double deductMoney) {
                this.deductMoney = deductMoney;
            }

            public double getPersonalTax() {
                return personalTax;
            }

            public void setPersonalTax(double personalTax) {
                this.personalTax = personalTax;
            }

            public double getActualMoney() {
                return actualMoney;
            }

            public void setActualMoney(double actualMoney) {
                this.actualMoney = actualMoney;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getGrantDate() {
                return grantDate;
            }

            public void setGrantDate(String grantDate) {
                this.grantDate = grantDate;
            }
        }
    }


}
package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;


//查询当前公司钱包冻结记录
public final class GetFirmFreezeRecordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/company/wallet/freeze_record";
    }

    private String date;
    private int pageNum;
    private int pageSize;

    public GetFirmFreezeRecordApi setDate(String createDate) {
        this.date = createDate;
        return this;
    }

    public GetFirmFreezeRecordApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmFreezeRecordApi setPageSize(int pageSize) {
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
            private int afterBalance;
            private int changeType;
            private int companyId;
            private String createDate;
            private int id;
            private int money;
            private int orderId;
            private String orderNo;
            private String remark;

            public int getAfterBalance() {
                return afterBalance;
            }

            public void setAfterBalance(int afterBalance) {
                this.afterBalance = afterBalance;
            }

            public int getChangeType() {
                return changeType;
            }

            public void setChangeType(int changeType) {
                this.changeType = changeType;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }


}
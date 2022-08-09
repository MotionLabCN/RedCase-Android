package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

//获取企业订单列表
public final class GetFirmOrderListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "businessOrder/getOrderList";
    }


    private String orderStatus;
    private int pageNum;
    private int pageSize;
    private String search;

    public GetFirmOrderListApi setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public GetFirmOrderListApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public GetFirmOrderListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public GetFirmOrderListApi setSearch(String search) {
        this.search = search;
        return this;
    }

    public final static class Bean {

        private Integer total;
        private Integer pageNum;
        private List<ListBean> list;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPageNum() {
            return pageNum;
        }

        public void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int id;
            private String orderNo;
            private int orderStatus;
            private String orderStatusName;
            private String workStartDate;
            private String realName;
            private String workDaysModeName;
            private String careerDirectionName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderStatusName() {
                return orderStatusName;
            }

            public void setOrderStatusName(String orderStatusName) {
                this.orderStatusName = orderStatusName;
            }

            public String getWorkStartDate() {
                return workStartDate;
            }

            public void setWorkStartDate(String workStartDate) {
                this.workStartDate = workStartDate;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getWorkDaysModeName() {
                return workDaysModeName;
            }

            public void setWorkDaysModeName(String workDaysModeName) {
                this.workDaysModeName = workDaysModeName;
            }

            public String getCareerDirectionName() {
                return careerDirectionName;
            }

            public void setCareerDirectionName(String careerDirectionName) {
                this.careerDirectionName = careerDirectionName;
            }
        }
    }

}
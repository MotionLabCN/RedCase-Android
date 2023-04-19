package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.tntlinking.tntdev.BuildConfig;

import java.util.List;

//获取公司下成员列表
public final class FirmMemberListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/member/page";
    }

    private int pageNum;
    private int pageSize = 20;


    public FirmMemberListApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public FirmMemberListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private String total;
        private String pageNum;
        private List<ListBean> list;

        public final static class ListBean {
            private int id;
            private String realName;
            private String positionName;
            private String mobile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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
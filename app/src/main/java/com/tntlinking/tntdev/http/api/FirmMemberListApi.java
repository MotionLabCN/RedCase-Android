package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;
import com.tntlinking.tntdev.BuildConfig;

import java.util.List;

//获取公司下成员列表
public final class FirmMemberListApi implements IRequestServer,IRequestApi {
    @Override   //https://talent.staging.tntlinking.com/api/new_gateway/tntlinking-member/sso/company/invite/members
    public String getHost() {
        return BuildConfig.HOST_URL + "/api/new_gateway/tntlinking-member/";
    }

    @Override
    public String getApi() {
        return "sso/company/member/page";
    }


    private int companyId;
    private int pageNum;
    private int pageSize = 20;

    public FirmMemberListApi setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

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
            private int auditStatus; //-1 拒绝 0 待审核 1 通过
            private String createDate;
            private String email;
            private int id;
            private double mobile;
            private String name;
            private String positionName;
            private String realName;


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
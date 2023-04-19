package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.util.List;

//邀请公司成员
public final class InviteMembersApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/invite/members";
    }


    private String email;   //
    private String mobile;   //
    private String position;   //
    private String realName;   //


    public InviteMembersApi setEmail(String email) {
        this.email = email;
        return this;
    }

    public InviteMembersApi setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public InviteMembersApi setPosition(String position) {
        this.position = position;
        return this;
    }

    public InviteMembersApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public final static class Bean {

        private double totalAmount;
        private double serviceAmount;
        private double freezeAmount;

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


    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}
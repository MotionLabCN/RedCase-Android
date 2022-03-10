package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class ToSignApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/signContract/sign";
    }

    private String bankCardId;
    private String bankName;
    private String idNumber;
    private String mobile;
    private String realName;


    public ToSignApi setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
        return this;
    }

    public ToSignApi setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public ToSignApi setIdNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public ToSignApi setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public ToSignApi setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

    public class Bean {
        private String contractUrl;

        public String getContractUrl() {
            return contractUrl;
        }

        public void setContractUrl(String contractUrl) {
            this.contractUrl = contractUrl;
        }
    }
}
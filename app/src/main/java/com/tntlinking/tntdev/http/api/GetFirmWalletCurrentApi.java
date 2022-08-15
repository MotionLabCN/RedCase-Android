package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;


//查询当前公司钱包账户信息
public final class GetFirmWalletCurrentApi implements IRequestApi {

    @Override
    public String getApi() {
        return "company/wallet/current";
    }


    public final static class Bean {

        private String availableMoney;
        private String balance;
        private String freezeMoney;
        private String id;
        private String rechargeDate;
        private int walletStatus;

        public String getAvailableMoney() {
            return availableMoney;
        }

        public void setAvailableMoney(String availableMoney) {
            this.availableMoney = availableMoney;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getFreezeMoney() {
            return freezeMoney;
        }

        public void setFreezeMoney(String freezeMoney) {
            this.freezeMoney = freezeMoney;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRechargeDate() {
            return rechargeDate;
        }

        public void setRechargeDate(String rechargeDate) {
            this.rechargeDate = rechargeDate;
        }

        public int getWalletStatus() {
            return walletStatus;
        }

        public void setWalletStatus(int walletStatus) {
            this.walletStatus = walletStatus;
        }
    }
}


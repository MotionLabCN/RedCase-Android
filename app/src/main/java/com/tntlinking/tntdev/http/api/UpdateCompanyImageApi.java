package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.File;

/**
 * desc   : 上传营业执照
 */
public final class UpdateCompanyImageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tntlinking-member/sso/app/companyMember/uploadBusinessLicense";
    }

    /**
     * 图片文件
     */
    private File multipartFile;

    public UpdateCompanyImageApi setFiles(File files) {
        this.multipartFile = files;
        return this;
    }

    public static class Bean {
        private String companyName;
        private String taxInvoice;
        private String businessLicense;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTaxInvoice() {
            return taxInvoice;
        }

        public void setTaxInvoice(String taxInvoice) {
            this.taxInvoice = taxInvoice;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }
    }
}
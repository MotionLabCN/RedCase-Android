package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 获取验证码
 */
public final class GetSignContractPDFApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/signContract/getPDFUrl";
    }


    public class Bean {
        private String pdfUrl;

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }
    }
}
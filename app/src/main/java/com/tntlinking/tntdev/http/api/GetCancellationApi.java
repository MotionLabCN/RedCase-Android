package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 开发者注销条件查询
 */
public final class GetCancellationApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/developer/cancellation/condition";
    }

    public static class ListBean {
        private String conditionTitle;
        private String conditionDescribe;
        private boolean flag;

        public String getConditionTitle() {
            return conditionTitle;
        }

        public void setConditionTitle(String conditionTitle) {
            this.conditionTitle = conditionTitle;
        }

        public String getConditionDescribe() {
            return conditionDescribe;
        }

        public void setConditionDescribe(String conditionDescribe) {
            this.conditionDescribe = conditionDescribe;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

}
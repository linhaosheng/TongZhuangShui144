package pro.haichuang.tzs144.model;

import android.text.TextUtils;

public class AccountHistoryModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"xjVal":"","xjDayRatio":"","xjWeekRatio":"","ptVal":"","ptDayRatio":"","ptWeekRatio":"","yjVal":"","yjDayRatio":"","yjWeekRatio":"","spVal":"","spDayRatio":"","spWeekRatio":"","jqVal":"","jqDayRatio":"","jqWeekRatio":""}
     */

    private int result;
    private String message;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * xjVal :
         * xjDayRatio :
         * xjWeekRatio :
         * ptVal :
         * ptDayRatio :
         * ptWeekRatio :
         * yjVal :
         * yjDayRatio :
         * yjWeekRatio :
         * spVal :
         * spDayRatio :
         * spWeekRatio :
         * jqVal :
         * jqDayRatio :
         * jqWeekRatio :
         */

        private String xjVal;
        private String xjDayRatio;
        private String xjWeekRatio;
        private String ptVal;
        private String ptDayRatio;
        private String ptWeekRatio;
        private String yjVal;
        private String yjDayRatio;
        private String yjWeekRatio;
        private String spVal;
        private String spDayRatio;
        private String spWeekRatio;
        private String jqVal;
        private String jqDayRatio;
        private String jqWeekRatio;

        private String yhqVal;  //优惠券价格
        private String yhqDayRatio;  //比昨日
        private String yhqWeekRatio;  //比上周


        public String getCouponPrice() {
            if (TextUtils.isEmpty(yhqVal)){
                return "0.0";
            }
            return yhqVal;
        }

        public void setCouponPrice(String couponPrice) {
            this.yhqVal = couponPrice;
        }

        public String getCouponDayRatio() {
            if (TextUtils.isEmpty(yhqDayRatio)){
                return "0.0";
            }
            return yhqDayRatio;
        }

        public void setCouponDayRatio(String couponDayRatio) {
            this.yhqDayRatio = couponDayRatio;
        }

        public String getCouponWeekRatio() {
            if (TextUtils.isEmpty(yhqWeekRatio)){
                return "0.0";
            }
            return yhqWeekRatio;
        }

        public void setCouponWeekRatio(String couponWeekRatio) {
            this.yhqWeekRatio = couponWeekRatio;
        }

        public String getXjVal() {
            return xjVal;
        }

        public void setXjVal(String xjVal) {
            this.xjVal = xjVal;
        }

        public String getXjDayRatio() {
            return xjDayRatio;
        }

        public void setXjDayRatio(String xjDayRatio) {
            this.xjDayRatio = xjDayRatio;
        }

        public String getXjWeekRatio() {
            return xjWeekRatio;
        }

        public void setXjWeekRatio(String xjWeekRatio) {
            this.xjWeekRatio = xjWeekRatio;
        }

        public String getPtVal() {
            return ptVal;
        }

        public void setPtVal(String ptVal) {
            this.ptVal = ptVal;
        }

        public String getPtDayRatio() {
            return ptDayRatio;
        }

        public void setPtDayRatio(String ptDayRatio) {
            this.ptDayRatio = ptDayRatio;
        }

        public String getPtWeekRatio() {
            return ptWeekRatio;
        }

        public void setPtWeekRatio(String ptWeekRatio) {
            this.ptWeekRatio = ptWeekRatio;
        }

        public String getYjVal() {
            return yjVal;
        }

        public void setYjVal(String yjVal) {
            this.yjVal = yjVal;
        }

        public String getYjDayRatio() {
            return yjDayRatio;
        }

        public void setYjDayRatio(String yjDayRatio) {
            this.yjDayRatio = yjDayRatio;
        }

        public String getYjWeekRatio() {
            return yjWeekRatio;
        }

        public void setYjWeekRatio(String yjWeekRatio) {
            this.yjWeekRatio = yjWeekRatio;
        }

        public String getSpVal() {
            return spVal;
        }

        public void setSpVal(String spVal) {
            this.spVal = spVal;
        }

        public String getSpDayRatio() {
            return spDayRatio;
        }

        public void setSpDayRatio(String spDayRatio) {
            this.spDayRatio = spDayRatio;
        }

        public String getSpWeekRatio() {
            return spWeekRatio;
        }

        public void setSpWeekRatio(String spWeekRatio) {
            this.spWeekRatio = spWeekRatio;
        }

        public String getJqVal() {
            return jqVal;
        }

        public void setJqVal(String jqVal) {
            this.jqVal = jqVal;
        }

        public String getJqDayRatio() {
            return jqDayRatio;
        }

        public void setJqDayRatio(String jqDayRatio) {
            this.jqDayRatio = jqDayRatio;
        }

        public String getJqWeekRatio() {
            return jqWeekRatio;
        }

        public void setJqWeekRatio(String jqWeekRatio) {
            this.jqWeekRatio = jqWeekRatio;
        }
    }
}

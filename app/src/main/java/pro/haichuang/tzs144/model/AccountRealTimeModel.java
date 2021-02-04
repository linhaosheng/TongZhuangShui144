package pro.haichuang.tzs144.model;

public class AccountRealTimeModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"xlVal":"","xlDayRatio":"","xlWeekRatio":"","yxddVal":"","yxddDayRatio":"","yxddWeekRatio":"","ddKhDayVal":"","ddKhDayRatio":"","ddKhWeekRatio":"","ysVal":"","ysDayRatio":"","ysWeekRatio":"","ssVal":"","ssDayRatio":"","ssWeekRatio":"","djPriceVal":"","djDayRatio":"","djWeekRatio":"","time":""}
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
         * xlVal :
         * xlDayRatio :
         * xlWeekRatio :
         * yxddVal :
         * yxddDayRatio :
         * yxddWeekRatio :
         * ddKhDayVal :
         * ddKhDayRatio :
         * ddKhWeekRatio :
         * ysVal :
         * ysDayRatio :
         * ysWeekRatio :
         * ssVal :
         * ssDayRatio :
         * ssWeekRatio :
         * djPriceVal :
         * djDayRatio :
         * djWeekRatio :
         * time :
         */

        private String xlVal;
        private String xlDayRatio;
        private String xlWeekRatio;
        private String yxddVal;
        private String yxddDayRatio;
        private String yxddWeekRatio;
        private String ddKhDayVal;
        private String ddKhDayRatio;
        private String ddKhWeekRatio;
        private String ysVal;
        private String ysDayRatio;
        private String ysWeekRatio;
        private String ssVal;
        private String ssDayRatio;
        private String ssWeekRatio;
        private String djPriceVal;
        private String djDayRatio;
        private String djWeekRatio;
        private String time;

        public String getXlVal() {
            return xlVal;
        }

        public void setXlVal(String xlVal) {
            this.xlVal = xlVal;
        }

        public String getXlDayRatio() {
            return xlDayRatio;
        }

        public void setXlDayRatio(String xlDayRatio) {
            this.xlDayRatio = xlDayRatio;
        }

        public String getXlWeekRatio() {
            return xlWeekRatio;
        }

        public void setXlWeekRatio(String xlWeekRatio) {
            this.xlWeekRatio = xlWeekRatio;
        }

        public String getYxddVal() {
            return yxddVal;
        }

        public void setYxddVal(String yxddVal) {
            this.yxddVal = yxddVal;
        }

        public String getYxddDayRatio() {
            return yxddDayRatio;
        }

        public void setYxddDayRatio(String yxddDayRatio) {
            this.yxddDayRatio = yxddDayRatio;
        }

        public String getYxddWeekRatio() {
            return yxddWeekRatio;
        }

        public void setYxddWeekRatio(String yxddWeekRatio) {
            this.yxddWeekRatio = yxddWeekRatio;
        }

        public String getDdKhDayVal() {
            return ddKhDayVal;
        }

        public void setDdKhDayVal(String ddKhDayVal) {
            this.ddKhDayVal = ddKhDayVal;
        }

        public String getDdKhDayRatio() {
            return ddKhDayRatio;
        }

        public void setDdKhDayRatio(String ddKhDayRatio) {
            this.ddKhDayRatio = ddKhDayRatio;
        }

        public String getDdKhWeekRatio() {
            return ddKhWeekRatio;
        }

        public void setDdKhWeekRatio(String ddKhWeekRatio) {
            this.ddKhWeekRatio = ddKhWeekRatio;
        }

        public String getYsVal() {
            return ysVal;
        }

        public void setYsVal(String ysVal) {
            this.ysVal = ysVal;
        }

        public String getYsDayRatio() {
            return ysDayRatio;
        }

        public void setYsDayRatio(String ysDayRatio) {
            this.ysDayRatio = ysDayRatio;
        }

        public String getYsWeekRatio() {
            return ysWeekRatio;
        }

        public void setYsWeekRatio(String ysWeekRatio) {
            this.ysWeekRatio = ysWeekRatio;
        }

        public String getSsVal() {
            return ssVal;
        }

        public void setSsVal(String ssVal) {
            this.ssVal = ssVal;
        }

        public String getSsDayRatio() {
            return ssDayRatio;
        }

        public void setSsDayRatio(String ssDayRatio) {
            this.ssDayRatio = ssDayRatio;
        }

        public String getSsWeekRatio() {
            return ssWeekRatio;
        }

        public void setSsWeekRatio(String ssWeekRatio) {
            this.ssWeekRatio = ssWeekRatio;
        }

        public String getDjPriceVal() {
            return djPriceVal;
        }

        public void setDjPriceVal(String djPriceVal) {
            this.djPriceVal = djPriceVal;
        }

        public String getDjDayRatio() {
            return djDayRatio;
        }

        public void setDjDayRatio(String djDayRatio) {
            this.djDayRatio = djDayRatio;
        }

        public String getDjWeekRatio() {
            return djWeekRatio;
        }

        public void setDjWeekRatio(String djWeekRatio) {
            this.djWeekRatio = djWeekRatio;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

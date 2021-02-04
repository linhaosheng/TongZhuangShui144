package pro.haichuang.tzs144.model;

public class ClientTrendModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"jxsCount":0,"xyCount":1,"xyDayCount":"-100","zdCount":0,"khCount":1,"yxDayCount":"-100","whWeekCount":"+100","zdWeekCount":"+100","xyWeekCount":"+100","khWeekCount":"+100","yxCount":1,"jxsDayCount":"+100","yxWeekCount":"+100","khDayCount":"-100","zdDayCount":"+100","jxsWeekCount":"+100","whCount":0,"whDayCount":"+100"}
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
         * jxsCount : 0
         * xyCount : 1
         * xyDayCount : -100
         * zdCount : 0
         * khCount : 1
         * yxDayCount : -100
         * whWeekCount : +100
         * zdWeekCount : +100
         * xyWeekCount : +100
         * khWeekCount : +100
         * yxCount : 1
         * jxsDayCount : +100
         * yxWeekCount : +100
         * khDayCount : -100
         * zdDayCount : +100
         * jxsWeekCount : +100
         * whCount : 0
         * whDayCount : +100
         */

        private int jxsCount;
        private int xyCount;
        private String xyDayCount;
        private int zdCount;
        private int khCount;
        private String yxDayCount;
        private String whWeekCount;
        private String zdWeekCount;
        private String xyWeekCount;
        private String khWeekCount;
        private int yxCount;
        private String jxsDayCount;
        private String yxWeekCount;
        private String khDayCount;
        private String zdDayCount;
        private String jxsWeekCount;
        private int whCount;
        private String whDayCount;

        public int getJxsCount() {
            return jxsCount;
        }

        public void setJxsCount(int jxsCount) {
            this.jxsCount = jxsCount;
        }

        public int getXyCount() {
            return xyCount;
        }

        public void setXyCount(int xyCount) {
            this.xyCount = xyCount;
        }

        public String getXyDayCount() {
            return xyDayCount;
        }

        public void setXyDayCount(String xyDayCount) {
            this.xyDayCount = xyDayCount;
        }

        public int getZdCount() {
            return zdCount;
        }

        public void setZdCount(int zdCount) {
            this.zdCount = zdCount;
        }

        public int getKhCount() {
            return khCount;
        }

        public void setKhCount(int khCount) {
            this.khCount = khCount;
        }

        public String getYxDayCount() {
            return yxDayCount;
        }

        public void setYxDayCount(String yxDayCount) {
            this.yxDayCount = yxDayCount;
        }

        public String getWhWeekCount() {
            return whWeekCount;
        }

        public void setWhWeekCount(String whWeekCount) {
            this.whWeekCount = whWeekCount;
        }

        public String getZdWeekCount() {
            return zdWeekCount;
        }

        public void setZdWeekCount(String zdWeekCount) {
            this.zdWeekCount = zdWeekCount;
        }

        public String getXyWeekCount() {
            return xyWeekCount;
        }

        public void setXyWeekCount(String xyWeekCount) {
            this.xyWeekCount = xyWeekCount;
        }

        public String getKhWeekCount() {
            return khWeekCount;
        }

        public void setKhWeekCount(String khWeekCount) {
            this.khWeekCount = khWeekCount;
        }

        public int getYxCount() {
            return yxCount;
        }

        public void setYxCount(int yxCount) {
            this.yxCount = yxCount;
        }

        public String getJxsDayCount() {
            return jxsDayCount;
        }

        public void setJxsDayCount(String jxsDayCount) {
            this.jxsDayCount = jxsDayCount;
        }

        public String getYxWeekCount() {
            return yxWeekCount;
        }

        public void setYxWeekCount(String yxWeekCount) {
            this.yxWeekCount = yxWeekCount;
        }

        public String getKhDayCount() {
            return khDayCount;
        }

        public void setKhDayCount(String khDayCount) {
            this.khDayCount = khDayCount;
        }

        public String getZdDayCount() {
            return zdDayCount;
        }

        public void setZdDayCount(String zdDayCount) {
            this.zdDayCount = zdDayCount;
        }

        public String getJxsWeekCount() {
            return jxsWeekCount;
        }

        public void setJxsWeekCount(String jxsWeekCount) {
            this.jxsWeekCount = jxsWeekCount;
        }

        public int getWhCount() {
            return whCount;
        }

        public void setWhCount(int whCount) {
            this.whCount = whCount;
        }

        public String getWhDayCount() {
            return whDayCount;
        }

        public void setWhDayCount(String whDayCount) {
            this.whDayCount = whDayCount;
        }
    }
}

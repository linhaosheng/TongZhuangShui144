package pro.haichuang.tzs144.model;

import java.util.List;

public class AccountListModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","orderTime":"","price":"","realName":"","time":""}]
     */

    private int result;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id :
         * orderTime :
         * price :
         * realName :
         * time :
         */

        private String id;
        private String orderTime;
        private String price;
        private String realName;
        private String time;
        private int settleStatus;

        public int getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(int settleStatus) {
            this.settleStatus = settleStatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

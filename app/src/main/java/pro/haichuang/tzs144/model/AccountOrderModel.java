package pro.haichuang.tzs144.model;

import java.util.List;

public class AccountOrderModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"no":"","type":"","customerName":"","phone":"","customerType":"","receivablePrice":"","realPrice":"","xjPrice":"","waterNum":"","couponNum":"","monthNum":""}]
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
         * no :
         * type :
         * customerName :
         * phone :
         * customerType :
         * receivablePrice :
         * realPrice :
         * xjPrice :
         * waterNum :
         * couponNum :
         * monthNum :
         */

        private int settleStatus = -1;
        private int id;
        private String no;
        private String type;
        private String customerName;
        private String phone;
        private String customerType;
        private String receivablePrice;
        private String realPrice;
        private String xjPrice;
        private float totalPrice;
        private String waterNum;
        private String couponNum;
        private String monthNum;
        private String deliveryTime;
        private String settleTime;


        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getSettleTime() {
            return settleTime;
        }

        public void setSettleTime(String settleTime) {
            this.settleTime = settleTime;
        }

        public float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(int settleStatus) {
            this.settleStatus = settleStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getReceivablePrice() {
            return receivablePrice;
        }

        public void setReceivablePrice(String receivablePrice) {
            this.receivablePrice = receivablePrice;
        }

        public String getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(String realPrice) {
            this.realPrice = realPrice;
        }

        public String getXjPrice() {
            return xjPrice;
        }

        public void setXjPrice(String xjPrice) {
            this.xjPrice = xjPrice;
        }

        public String getWaterNum() {
            return waterNum;
        }

        public void setWaterNum(String waterNum) {
            this.waterNum = waterNum;
        }

        public String getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(String couponNum) {
            this.couponNum = couponNum;
        }

        public String getMonthNum() {
            return monthNum;
        }

        public void setMonthNum(String monthNum) {
            this.monthNum = monthNum;
        }
    }
}

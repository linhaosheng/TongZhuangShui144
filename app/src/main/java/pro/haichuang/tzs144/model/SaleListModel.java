package pro.haichuang.tzs144.model;

import java.util.List;

public class SaleListModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","orderType":"","customerName":"","customerPhone":"","customerType":"","addressName":"","address":"","totalPrice":"","receivablePrice":"","realPrice":"","createTime":"","salesDistance":""}]
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
         * orderType :
         * customerName :
         * customerPhone :
         * customerType :
         * addressName :
         * address :
         * totalPrice :
         * receivablePrice :
         * realPrice :
         * createTime :
         * salesDistance :
         */

        private String id;
        private String orderType;
        private String customerName;
        private String customerPhone;
        private String customerType;
        private String addressName;
        private String address;
        private String totalPrice;
        private String receivablePrice;
        private String realPrice;
        private String createTime;
        private String salesDistance;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSalesDistance() {
            return salesDistance;
        }

        public void setSalesDistance(String salesDistance) {
            this.salesDistance = salesDistance;
        }
    }
}

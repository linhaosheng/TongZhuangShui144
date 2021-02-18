package pro.haichuang.tzs144.model;

import java.util.List;

public class ClientListModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","customerName":"","phone":"","typeName":"","orderCount":"","saleCount":"","monthRatio":"","monthSaleRatio":"","yearRatio":"","saleYearRatio":"","isMaintain":""}]
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
         * customerName :
         * phone :
         * typeName :
         * orderCount :
         * saleCount :
         * monthRatio :
         * monthSaleRatio :
         * yearRatio :
         * saleYearRatio :
         * isMaintain :
         */

        private String id;
        private String customerName;
        private String phone;
        private String typeName;
        private String orderCount;
        private String saleCount;
        private String monthRatio;
        private String monthSaleRatio;
        private String yearRatio;
        private String saleYearRatio;
        private boolean isMaintain;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getSaleCount() {
            return saleCount;
        }

        public void setSaleCount(String saleCount) {
            this.saleCount = saleCount;
        }

        public String getMonthRatio() {
            return monthRatio;
        }

        public void setMonthRatio(String monthRatio) {
            this.monthRatio = monthRatio;
        }

        public String getMonthSaleRatio() {
            return monthSaleRatio;
        }

        public void setMonthSaleRatio(String monthSaleRatio) {
            this.monthSaleRatio = monthSaleRatio;
        }

        public String getYearRatio() {
            return yearRatio;
        }

        public void setYearRatio(String yearRatio) {
            this.yearRatio = yearRatio;
        }

        public String getSaleYearRatio() {
            return saleYearRatio;
        }

        public void setSaleYearRatio(String saleYearRatio) {
            this.saleYearRatio = saleYearRatio;
        }

        public boolean getIsMaintain() {
            return isMaintain;
        }

        public void setIsMaintain(boolean isMaintain) {
            this.isMaintain = isMaintain;
        }
    }
}

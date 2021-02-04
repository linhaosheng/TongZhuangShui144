package pro.haichuang.tzs144.model;

import java.util.List;

public class AccountListDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"time":"","xjPrice":"","wxPrice":"","mtPrice":"","elPrice":"","waterNum":"","couponNum":"","monthNum":"","list":[{"no":"","type":"","customerName":"","phone":"","customerType":"","totalPrice":"","receivablePrice":"","realPrice":"","xjPrice":"","waterNum":"","couponNum":"","monthNum":""}]}]
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
         * time :
         * xjPrice :
         * wxPrice :
         * mtPrice :
         * elPrice :
         * waterNum :
         * couponNum :
         * monthNum :
         * list : [{"no":"","type":"","customerName":"","phone":"","customerType":"","totalPrice":"","receivablePrice":"","realPrice":"","xjPrice":"","waterNum":"","couponNum":"","monthNum":""}]
         */

        private String time;
        private String xjPrice;
        private String wxPrice;
        private String mtPrice;
        private String elPrice;
        private String waterNum;
        private String couponNum;
        private String monthNum;
        private List<ListBean> list;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getXjPrice() {
            return xjPrice;
        }

        public void setXjPrice(String xjPrice) {
            this.xjPrice = xjPrice;
        }

        public String getWxPrice() {
            return wxPrice;
        }

        public void setWxPrice(String wxPrice) {
            this.wxPrice = wxPrice;
        }

        public String getMtPrice() {
            return mtPrice;
        }

        public void setMtPrice(String mtPrice) {
            this.mtPrice = mtPrice;
        }

        public String getElPrice() {
            return elPrice;
        }

        public void setElPrice(String elPrice) {
            this.elPrice = elPrice;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * no :
             * type :
             * customerName :
             * phone :
             * customerType :
             * totalPrice :
             * receivablePrice :
             * realPrice :
             * xjPrice :
             * waterNum :
             * couponNum :
             * monthNum :
             */

            private String no;
            private String type;
            private String customerName;
            private String phone;
            private String customerType;
            private String totalPrice;
            private String receivablePrice;
            private String realPrice;
            private String xjPrice;
            private String waterNum;
            private String couponNum;
            private String monthNum;

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
}

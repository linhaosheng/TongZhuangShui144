package pro.haichuang.tzs144.model;

import java.util.List;

public class AccountListDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"couponNum":2,"waterNum":0,"mtPrice":0,"monthNum":77,"time":"2021-03-29 17:56:05","list":[{"couponNum":0,"no":"LR202103291704006","totalPrice":6,"type":3,"customerName":"高密二中","waterNum":0,"customerType":"协议客户","phone":"18866768118","receivablePrice":6,"monthNum":6,"id":358,"xjPrice":0,"realPrice":0}],"xjPrice":0,"wxPrice":0,"elPrice":0}
     */

    private Integer result;
    private String message;
    private DataBean data;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
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
         * couponNum : 2
         * waterNum : 0
         * mtPrice : 0
         * monthNum : 77
         * time : 2021-03-29 17:56:05
         * list : [{"couponNum":0,"no":"LR202103291704006","totalPrice":6,"type":3,"customerName":"高密二中","waterNum":0,"customerType":"协议客户","phone":"18866768118","receivablePrice":6,"monthNum":6,"id":358,"xjPrice":0,"realPrice":0}]
         * xjPrice : 0
         * wxPrice : 0
         * elPrice : 0
         */

        private Integer couponNum;
        private Integer waterNum;
        private Integer mtPrice;
        private Integer monthNum;
        private String time;
        private List<ListBean> list;
        private Integer xjPrice;
        private Integer wxPrice;
        private Integer elPrice;

        public Integer getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(Integer couponNum) {
            this.couponNum = couponNum;
        }

        public Integer getWaterNum() {
            return waterNum;
        }

        public void setWaterNum(Integer waterNum) {
            this.waterNum = waterNum;
        }

        public Integer getMtPrice() {
            return mtPrice;
        }

        public void setMtPrice(Integer mtPrice) {
            this.mtPrice = mtPrice;
        }

        public Integer getMonthNum() {
            return monthNum;
        }

        public void setMonthNum(Integer monthNum) {
            this.monthNum = monthNum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public Integer getXjPrice() {
            return xjPrice;
        }

        public void setXjPrice(Integer xjPrice) {
            this.xjPrice = xjPrice;
        }

        public Integer getWxPrice() {
            return wxPrice;
        }

        public void setWxPrice(Integer wxPrice) {
            this.wxPrice = wxPrice;
        }

        public Integer getElPrice() {
            return elPrice;
        }

        public void setElPrice(Integer elPrice) {
            this.elPrice = elPrice;
        }

        public static class ListBean {
            /**
             * couponNum : 0
             * no : LR202103291704006
             * totalPrice : 6
             * type : 3
             * customerName : 高密二中
             * waterNum : 0
             * customerType : 协议客户
             * phone : 18866768118
             * receivablePrice : 6
             * monthNum : 6
             * id : 358
             * xjPrice : 0
             * realPrice : 0
             */

            private Integer couponNum;
            private String no;
            private Integer totalPrice;
            private Integer type;
            private String customerName;
            private Integer waterNum;
            private String customerType;
            private String phone;
            private Integer receivablePrice;
            private Integer monthNum;
            private Integer id;
            private Integer xjPrice;
            private Integer realPrice;

            public Integer getCouponNum() {
                return couponNum;
            }

            public void setCouponNum(Integer couponNum) {
                this.couponNum = couponNum;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public Integer getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Integer totalPrice) {
                this.totalPrice = totalPrice;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public Integer getWaterNum() {
                return waterNum;
            }

            public void setWaterNum(Integer waterNum) {
                this.waterNum = waterNum;
            }

            public String getCustomerType() {
                return customerType;
            }

            public void setCustomerType(String customerType) {
                this.customerType = customerType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Integer getReceivablePrice() {
                return receivablePrice;
            }

            public void setReceivablePrice(Integer receivablePrice) {
                this.receivablePrice = receivablePrice;
            }

            public Integer getMonthNum() {
                return monthNum;
            }

            public void setMonthNum(Integer monthNum) {
                this.monthNum = monthNum;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getXjPrice() {
                return xjPrice;
            }

            public void setXjPrice(Integer xjPrice) {
                this.xjPrice = xjPrice;
            }

            public Integer getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(Integer realPrice) {
                this.realPrice = realPrice;
            }
        }
    }
}

package pro.haichuang.tzs144.model;

import java.util.List;

public class OrderDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"address":"四川省成都市武侯区肖家河街道恩威集团(创业路)","orderNo":"LR202101131509004","totalPrice":10,"goodsList":[{"waterNum":1,"waterDeductNum":1,"goodsPrice":1,"couponDeductNum":null,"couponImg":"","materialList":[""],"goodsName":"新商品-A","goodsNum":10,"monthImg":"","monthDeductNum":null}],"salesDistance":0,"customerName":"老李","customerTypeName":"终端客户","customerPhone":"15123456789","addressName":"测试地址","receivablePrice":1,"time":"2021-01-13 16:16:25","createName":"骑龙3","realPrice":9}
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
         * address : 四川省成都市武侯区肖家河街道恩威集团(创业路)
         * orderNo : LR202101131509004
         * totalPrice : 10
         * goodsList : [{"waterNum":1,"waterDeductNum":1,"goodsPrice":1,"couponDeductNum":null,"couponImg":"","materialList":[""],"goodsName":"新商品-A","goodsNum":10,"monthImg":"","monthDeductNum":null}]
         * salesDistance : 0
         * customerName : 老李
         * customerTypeName : 终端客户
         * customerPhone : 15123456789
         * addressName : 测试地址
         * receivablePrice : 1
         * time : 2021-01-13 16:16:25
         * createName : 骑龙3
         * realPrice : 9
         */

        private String address;
        private String orderNo;
        private int totalPrice;
        private int salesDistance;
        private String customerName;
        private String customerTypeName;
        private String customerPhone;
        private String addressName;
        private int receivablePrice;
        private String time;
        private String createName;
        private int realPrice;
        private List<GoodsListBean> goodsList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getSalesDistance() {
            return salesDistance;
        }

        public void setSalesDistance(int salesDistance) {
            this.salesDistance = salesDistance;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerTypeName() {
            return customerTypeName;
        }

        public void setCustomerTypeName(String customerTypeName) {
            this.customerTypeName = customerTypeName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public int getReceivablePrice() {
            return receivablePrice;
        }

        public void setReceivablePrice(int receivablePrice) {
            this.receivablePrice = receivablePrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
            this.realPrice = realPrice;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * waterNum : 1
             * waterDeductNum : 1
             * goodsPrice : 1
             * couponDeductNum : null
             * couponImg :
             * materialList : [""]
             * goodsName : 新商品-A
             * goodsNum : 10
             * monthImg :
             * monthDeductNum : null
             */

            private int waterNum;
            private int waterDeductNum;
            private int goodsPrice;
            private Object couponDeductNum;
            private String couponImg;
            private String goodsName;
            private int goodsNum;
            private String monthImg;
            private Object monthDeductNum;
            private List<String> materialList;

            public int getWaterNum() {
                return waterNum;
            }

            public void setWaterNum(int waterNum) {
                this.waterNum = waterNum;
            }

            public int getWaterDeductNum() {
                return waterDeductNum;
            }

            public void setWaterDeductNum(int waterDeductNum) {
                this.waterDeductNum = waterDeductNum;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public Object getCouponDeductNum() {
                return couponDeductNum;
            }

            public void setCouponDeductNum(Object couponDeductNum) {
                this.couponDeductNum = couponDeductNum;
            }

            public String getCouponImg() {
                return couponImg;
            }

            public void setCouponImg(String couponImg) {
                this.couponImg = couponImg;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getMonthImg() {
                return monthImg;
            }

            public void setMonthImg(String monthImg) {
                this.monthImg = monthImg;
            }

            public Object getMonthDeductNum() {
                return monthDeductNum;
            }

            public void setMonthDeductNum(Object monthDeductNum) {
                this.monthDeductNum = monthDeductNum;
            }

            public List<String> getMaterialList() {
                return materialList;
            }

            public void setMaterialList(List<String> materialList) {
                this.materialList = materialList;
            }
        }
    }
}

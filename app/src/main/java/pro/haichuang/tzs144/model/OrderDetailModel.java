package pro.haichuang.tzs144.model;

import java.util.List;

public class OrderDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"deliveryStatus":"","address":"四川省成都市武侯区肖家河街道恩威集团(创业路)","orderNo":"LR202101131509004","timeTitle":"今天","timeRange":"","timeStatus":"","orderType":"","payMode":"","completeTime":"","totalPrice":10,"goodsList":[{"waterNum":1,"waterDeductNum":1,"goodsPrice":1,"couponDeductNum":null,"couponImg":"","materialList":[""],"goodsName":"新商品-A","goodsNum":10,"monthImg":"","monthDeductNum":null}],"customerName":"老李","customerTypeName":"终端客户","customerPhone":"15123456789","addressName":"测试地址","receivablePrice":1,"realPrice":9,"distance":""}
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
         * deliveryStatus :
         * address : 四川省成都市武侯区肖家河街道恩威集团(创业路)
         * orderNo : LR202101131509004
         * timeTitle : 今天
         * timeRange :
         * timeStatus :
         * orderType :
         * payMode :
         * completeTime :
         * totalPrice : 10
         * goodsList : [{"waterNum":1,"waterDeductNum":1,"goodsPrice":1,"couponDeductNum":null,"couponImg":"","materialList":[""],"goodsName":"新商品-A","goodsNum":10,"monthImg":"","monthDeductNum":null}]
         * customerName : 老李
         * customerTypeName : 终端客户
         * customerPhone : 15123456789
         * addressName : 测试地址
         * receivablePrice : 1
         * realPrice : 9
         * distance :
         */

        private int orderStatus;
        private int salesDistance;
        private String createName;
        private String time;
        private int deliveryStatus;
        private String address;
        private String orderNo;
        private String timeTitle;
        private String timeRange;
        private String timeStatus;
        private String orderType;
        private String payMode;
        private String completeTime;
        private int totalPrice;
        private String customerName;
        private String customerTypeName;
        private String customerPhone;
        private String addressName;
        private int receivablePrice;
        private int realPrice;
        private String distance;
        private List<GoodsListBean> goodsList;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getSalesDistance() {
            return salesDistance;
        }

        public void setSalesDistance(int salesDistance) {
            this.salesDistance = salesDistance;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(int deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

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

        public String getTimeTitle() {
            return timeTitle;
        }

        public void setTimeTitle(String timeTitle) {
            this.timeTitle = timeTitle;
        }

        public String getTimeRange() {
            return timeRange;
        }

        public void setTimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getTimeStatus() {
            return timeStatus;
        }

        public void setTimeStatus(String timeStatus) {
            this.timeStatus = timeStatus;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPayMode() {
            return payMode;
        }

        public void setPayMode(String payMode) {
            this.payMode = payMode;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
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

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
            this.realPrice = realPrice;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

package pro.haichuang.tzs144.model;

import java.util.List;

public class OrderDetailDataModel {
    /**
     * result : 1
     * message : 获取成功!
     * data : {"orderType":"电话订单","address":"四川省成都市双流区华阳镇街道南湖国际社区南湖国际社区南区","orderNo":"LR202103051110001","distance":1241904,"timeTitle":"今天","totalPrice":75,"payMode":"现金支付","goodsList":[{"orderGoodsId":63,"waterNum":null,"waterDeductNum":null,"goodsSpecsName":"18.9L","goodsId":91,"bindMaterList":[{"name":"其他通用桶","id":81}],"goodsPrice":15,"couponDeductNum":null,"materialList":[],"goodsName":"怡宝纯净水","goodsNum":1,"monthDeductNum":null},{"orderGoodsId":64,"waterNum":null,"waterDeductNum":null,"goodsSpecsName":"16.9L","goodsId":88,"bindMaterList":[{"name":"老版桃花液桶","id":78}],"goodsPrice":20,"couponDeductNum":null,"materialList":[],"goodsName":"日之泉天然矿泉水","goodsNum":3,"monthDeductNum":null}],"completeTime":"","customerName":"小王","customerTypeName":"终端客户","customerPhone":"18123456789","addressName":"地址1","receivablePrice":75,"deliveryStatus":1,"timeStatus":"","timeRange":"12:56-19:56","realPrice":75}
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
         * orderType : 电话订单
         * address : 四川省成都市双流区华阳镇街道南湖国际社区南湖国际社区南区
         * orderNo : LR202103051110001
         * distance : 1241904
         * timeTitle : 今天
         * totalPrice : 75
         * payMode : 现金支付
         * goodsList : [{"orderGoodsId":63,"waterNum":null,"waterDeductNum":null,"goodsSpecsName":"18.9L","goodsId":91,"bindMaterList":[{"name":"其他通用桶","id":81}],"goodsPrice":15,"couponDeductNum":null,"materialList":[],"goodsName":"怡宝纯净水","goodsNum":1,"monthDeductNum":null},{"orderGoodsId":64,"waterNum":null,"waterDeductNum":null,"goodsSpecsName":"16.9L","goodsId":88,"bindMaterList":[{"name":"老版桃花液桶","id":78}],"goodsPrice":20,"couponDeductNum":null,"materialList":[],"goodsName":"日之泉天然矿泉水","goodsNum":3,"monthDeductNum":null}]
         * completeTime :
         * customerName : 小王
         * customerTypeName : 终端客户
         * customerPhone : 18123456789
         * addressName : 地址1
         * receivablePrice : 75
         * deliveryStatus : 1
         * timeStatus :
         * timeRange : 12:56-19:56
         * realPrice : 75
         */

        private String orderType;
        private String address;
        private String orderNo;
        private int distance;
        private String timeTitle;
        private double totalPrice;
        private String payMode;
        private String completeTime;
        private String customerName;
        private String customerTypeName;
        private String customerPhone;
        private String addressName;
        private double receivablePrice;
        private int deliveryStatus;
        private String timeStatus;
        private String timeRange;
        private double realPrice;
        private int customerId;

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        private List<OrderDetailModel.DataBean.GoodsListBean> goodsList;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getTimeTitle() {
            return timeTitle;
        }

        public void setTimeTitle(String timeTitle) {
            this.timeTitle = timeTitle;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
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

        public double getReceivablePrice() {
            return receivablePrice;
        }

        public void setReceivablePrice(double receivablePrice) {
            this.receivablePrice = receivablePrice;
        }

        public int getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(int deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public String getTimeStatus() {
            return timeStatus;
        }

        public void setTimeStatus(String timeStatus) {
            this.timeStatus = timeStatus;
        }

        public String getTimeRange() {
            return timeRange;
        }

        public void setTimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public double getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(double realPrice) {
            this.realPrice = realPrice;
        }

        public List<OrderDetailModel.DataBean.GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<OrderDetailModel.DataBean.GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * orderGoodsId : 63
             * waterNum : null
             * waterDeductNum : null
             * goodsSpecsName : 18.9L
             * goodsId : 91
             * bindMaterList : [{"name":"其他通用桶","id":81}]
             * goodsPrice : 15
             * couponDeductNum : null
             * materialList : []
             * goodsName : 怡宝纯净水
             * goodsNum : 1
             * monthDeductNum : null
             */

            private int orderGoodsId;
            private Object waterNum;
            private Object waterDeductNum;
            private String goodsSpecsName;
            private int goodsId;
            private double goodsPrice;
            private Object couponDeductNum;
            private String goodsName;
            private int goodsNum;
            private Object monthDeductNum;
            private List<BindMaterListBean> bindMaterList;
            private List<?> materialList;
            private String monthImg;
            private String couponImg;

            public String getMonthImg() {
                return monthImg;
            }

            public void setMonthImg(String monthImg) {
                this.monthImg = monthImg;
            }

            public String getCouponImg() {
                return couponImg;
            }

            public void setCouponImg(String couponImg) {
                this.couponImg = couponImg;
            }

            public int getOrderGoodsId() {
                return orderGoodsId;
            }

            public void setOrderGoodsId(int orderGoodsId) {
                this.orderGoodsId = orderGoodsId;
            }

            public Object getWaterNum() {
                return waterNum;
            }

            public void setWaterNum(Object waterNum) {
                this.waterNum = waterNum;
            }

            public Object getWaterDeductNum() {
                return waterDeductNum;
            }

            public void setWaterDeductNum(Object waterDeductNum) {
                this.waterDeductNum = waterDeductNum;
            }

            public String getGoodsSpecsName() {
                return goodsSpecsName;
            }

            public void setGoodsSpecsName(String goodsSpecsName) {
                this.goodsSpecsName = goodsSpecsName;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public Object getCouponDeductNum() {
                return couponDeductNum;
            }

            public void setCouponDeductNum(Object couponDeductNum) {
                this.couponDeductNum = couponDeductNum;
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

            public Object getMonthDeductNum() {
                return monthDeductNum;
            }

            public void setMonthDeductNum(Object monthDeductNum) {
                this.monthDeductNum = monthDeductNum;
            }

            public List<BindMaterListBean> getBindMaterList() {
                return bindMaterList;
            }

            public void setBindMaterList(List<BindMaterListBean> bindMaterList) {
                this.bindMaterList = bindMaterList;
            }

            public List<?> getMaterialList() {
                return materialList;
            }

            public void setMaterialList(List<?> materialList) {
                this.materialList = materialList;
            }

            public static class BindMaterListBean {
                /**
                 * name : 其他通用桶
                 * id : 81
                 */

                private String name;
                private int id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}

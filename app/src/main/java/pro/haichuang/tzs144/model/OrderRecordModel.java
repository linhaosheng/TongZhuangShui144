package pro.haichuang.tzs144.model;

import java.util.List;

public class OrderRecordModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"orderCount":"","saleCount":"","dayOrderRatio":"","weekOrderRatio":"","daySaleRatio":"","weekSaleRatio":"","orderList":[{"id":"","orderNo":"","timeTitle":"","timeRange":"","timeStatus":"","orderType":"","status":"","custoemrName":"","customerPhone":"","customerType":"","address":"","distance":"","goodsList":[{"category":"","list":[{"goodsImg":"","goodsName":"","unit":"","price":""}]}],"goodsTypeNum":"","goodsNum":"","goodsTopInfo":""}]}
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
         * orderCount :
         * saleCount :
         * dayOrderRatio :
         * weekOrderRatio :
         * daySaleRatio :
         * weekSaleRatio :
         * orderList : [{"id":"","orderNo":"","timeTitle":"","timeRange":"","timeStatus":"","orderType":"","status":"","custoemrName":"","customerPhone":"","customerType":"","address":"","distance":"","goodsList":[{"category":"","list":[{"goodsImg":"","goodsName":"","unit":"","price":""}]}],"goodsTypeNum":"","goodsNum":"","goodsTopInfo":""}]
         */

        private String orderCount;
        private String saleCount;
        private String dayOrderRatio;
        private String weekOrderRatio;
        private String daySaleRatio;
        private String weekSaleRatio;
        private List<OrderListBean> orderList;

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

        public String getDayOrderRatio() {
            return dayOrderRatio;
        }

        public void setDayOrderRatio(String dayOrderRatio) {
            this.dayOrderRatio = dayOrderRatio;
        }

        public String getWeekOrderRatio() {
            return weekOrderRatio;
        }

        public void setWeekOrderRatio(String weekOrderRatio) {
            this.weekOrderRatio = weekOrderRatio;
        }

        public String getDaySaleRatio() {
            return daySaleRatio;
        }

        public void setDaySaleRatio(String daySaleRatio) {
            this.daySaleRatio = daySaleRatio;
        }

        public String getWeekSaleRatio() {
            return weekSaleRatio;
        }

        public void setWeekSaleRatio(String weekSaleRatio) {
            this.weekSaleRatio = weekSaleRatio;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * id :
             * orderNo :
             * timeTitle :
             * timeRange :
             * timeStatus :
             * orderType :
             * status :
             * custoemrName :
             * customerPhone :
             * customerType :
             * address :
             * distance :
             * goodsList : [{"category":"","list":[{"goodsImg":"","goodsName":"","unit":"","price":""}]}]
             * goodsTypeNum :
             * goodsNum :
             * goodsTopInfo :
             */

            private String id;
            private String orderNo;
            private String timeTitle;
            private String timeRange;
            private String timeStatus;
            private String orderType;
            private int status;
            private String custoemrName;
            private String customerPhone;
            private String customerType;
            private String address;
            private String distance;
            private String goodsTypeNum;
            private String goodsNum;
            private String goodsTopInfo;
            private List<GoodsListBean> goodsList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCustoemrName() {
                return custoemrName;
            }

            public void setCustoemrName(String custoemrName) {
                this.custoemrName = custoemrName;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getGoodsTypeNum() {
                return goodsTypeNum;
            }

            public void setGoodsTypeNum(String goodsTypeNum) {
                this.goodsTypeNum = goodsTypeNum;
            }

            public String getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(String goodsNum) {
                this.goodsNum = goodsNum;
            }

            public String getGoodsTopInfo() {
                return goodsTopInfo;
            }

            public void setGoodsTopInfo(String goodsTopInfo) {
                this.goodsTopInfo = goodsTopInfo;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean {
                /**
                 * category :
                 * list : [{"goodsImg":"","goodsName":"","unit":"","price":""}]
                 */

                private String category;
                private List<ListBean> list;

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    /**
                     * goodsImg :
                     * goodsName :
                     * unit :
                     * price :
                     */

                    private String goodsImg;
                    private String goodsName;
                    private String unit;
                    private String price;

                    public String getGoodsImg() {
                        return goodsImg;
                    }

                    public void setGoodsImg(String goodsImg) {
                        this.goodsImg = goodsImg;
                    }

                    public String getGoodsName() {
                        return goodsName;
                    }

                    public void setGoodsName(String goodsName) {
                        this.goodsName = goodsName;
                    }

                    public String getUnit() {
                        return unit;
                    }

                    public void setUnit(String unit) {
                        this.unit = unit;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }
                }
            }
        }
    }
}

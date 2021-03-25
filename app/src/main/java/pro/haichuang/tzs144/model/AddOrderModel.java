package pro.haichuang.tzs144.model;

import java.util.List;

public class AddOrderModel {


    /**
     * verification :
     * orderType :
     * customerId :
     * addressId :
     * goodsList : [{"goodsId":"","num":"","goodsPrice":"","materials":[{"materialId":"","num":""}],"deductWater":{"waterGoodsId":"","num":"","deductNum":""},"deductCoupon":{"couponImg":"","deductNum":""},"deductMonth":{"monthImg":"","deductNum":""}}]
     */

    private String verification;
    private String orderType;
    private String customerId;
    private String addressId;
    private List<GoodsListBean> goodsList;

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * goodsId :
         * num :
         * goodsPrice :
         * materials : [{"materialId":"","num":""}]
         * deductWater : {"waterGoodsId":"","num":"","deductNum":""}
         * deductCoupon : {"couponImg":"","deductNum":""}
         * deductMonth : {"monthImg":"","deductNum":""}
         */

        private String orderGoodsId;
        private String goodName;
        private String goodsId;
        private String num;
        private String goodsPrice;
        private String specs;
        private DeductWaterBean deductWater;
        private DeductCouponBean deductCoupon;
        private DeductMonthBean deductMonth;
        private List<MaterialModel.DataBean> materials;

        public List<MaterialModel.DataBean> getMaterials() {
            return materials;
        }

        public void setMaterials(List<MaterialModel.DataBean> materials) {
            this.materials = materials;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public DeductWaterBean getDeductWater() {
            return deductWater;
        }

        public void setDeductWater(DeductWaterBean deductWater) {
            this.deductWater = deductWater;
        }

        public DeductCouponBean getDeductCoupon() {
            return deductCoupon;
        }

        public void setDeductCoupon(DeductCouponBean deductCoupon) {
            this.deductCoupon = deductCoupon;
        }

        public DeductMonthBean getDeductMonth() {
            return deductMonth;
        }

        public void setDeductMonth(DeductMonthBean deductMonth) {
            this.deductMonth = deductMonth;
        }


        public static class DeductWaterBean {
            /**
             * waterGoodsId :
             * num :
             * deductNum :
             */

            private String waterGoodsId;
            private String num = "0";
            private String deductNum = "0";

            public String getWaterGoodsId() {
                return waterGoodsId;
            }

            public void setWaterGoodsId(String waterGoodsId) {
                this.waterGoodsId = waterGoodsId;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getDeductNum() {
                return deductNum;
            }

            public void setDeductNum(String deductNum) {
                this.deductNum = deductNum;
            }
        }

        public static class DeductCouponBean {
            /**
             * couponImg :
             * deductNum :
             */

            private String couponImg;
            private String deductNum = "0";

            public String getCouponImg() {
                return couponImg;
            }

            public void setCouponImg(String couponImg) {
                this.couponImg = couponImg;
            }

            public String getDeductNum() {
                return deductNum;
            }

            public void setDeductNum(String deductNum) {
                this.deductNum = deductNum;
            }
        }

        public static class DeductMonthBean {
            /**
             * monthImg :
             * deductNum :
             */

            private String monthImg;
            private String deductNum = "0";

            public String getMonthImg() {
                return monthImg;
            }

            public void setMonthImg(String monthImg) {
                this.monthImg = monthImg;
            }

            public String getDeductNum() {
                return deductNum;
            }

            public void setDeductNum(String deductNum) {
                this.deductNum = deductNum;
            }
        }

    }
}

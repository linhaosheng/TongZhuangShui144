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

        private String goodName;
        private String goodsId;
        private String num;
        private String goodsPrice;
        private DeductWaterBean deductWater;
        private DeductCouponBean deductCoupon;
        private DeductMonthBean deductMonth;
        private List<MaterialsBean> materials;

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

        public List<MaterialsBean> getMaterials() {
            return materials;
        }

        public void setMaterials(List<MaterialsBean> materials) {
            this.materials = materials;
        }

        public static class DeductWaterBean {
            /**
             * waterGoodsId :
             * num :
             * deductNum :
             */

            private String waterGoodsId;
            private String num;
            private String deductNum;

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
            private String deductNum;

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
            private String deductNum;

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

        public static class MaterialsBean {
            /**
             * materialId :
             * num :
             */

            private String materialName;
            private String materialId;
            private String num;

            public String getMaterialName() {
                return materialName;
            }

            public void setMaterialName(String materialName) {
                this.materialName = materialName;
            }

            public String getMaterialId() {
                return materialId;
            }

            public void setMaterialId(String materialId) {
                this.materialId = materialId;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}

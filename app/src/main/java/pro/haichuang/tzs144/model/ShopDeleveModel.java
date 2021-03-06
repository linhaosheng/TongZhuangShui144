package pro.haichuang.tzs144.model;

import java.util.List;

public class ShopDeleveModel {


    /**
     * id :
     * goodsList : [{"orderGoodsId":"","materials":[{"materialId":"","num":""}],"deductWater":{"waterGoodsId":"","num":"","deductNum":""},"deductCoupon":{"couponImg":"","deductNum":""},"deductMonth":{"monthImg":"","deductNum":""}}]
     */

    private String id;
    private List<GoodsListBean> goodsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * orderGoodsId :
         * materials : [{"materialId":"","num":""}]
         * deductWater : {"waterGoodsId":"","num":"","deductNum":""}
         * deductCoupon : {"couponImg":"","deductNum":""}
         * deductMonth : {"monthImg":"","deductNum":""}
         */

        private String orderGoodsId;
        private DeductWaterBean deductWater;
        private DeductCouponBean deductCoupon;
        private DeductMonthBean deductMonth;
        private List<MaterialsBean> materials;

        public String getOrderGoodsId() {
            return orderGoodsId;
        }

        public void setOrderGoodsId(String orderGoodsId) {
            this.orderGoodsId = orderGoodsId;
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

            private String materialId;
            private String num;

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

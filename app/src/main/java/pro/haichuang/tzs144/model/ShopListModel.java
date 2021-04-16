package pro.haichuang.tzs144.model;

import java.util.List;

public class ShopListModel {


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
        private String totalPage;
        private List<DataListBean> dataList;

        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            private String id;
            private String goodsName;
            private String goodsPrice;
            private String unitName;
            private String typeName;
            private String categoryName;
            private String brandName;
            private String specsName;
            private String stockNum;
            private String goodsImg;
            private String putTime;
            private String indexFlag;
            private String goodsStatus;
            private boolean isCheck;
            private String unintNames;

            public String getUnintNames() {
                return unintNames;
            }

            public void setUnintNames(String unintNames) {
                this.unintNames = unintNames;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getSpecsName() {
                return specsName;
            }

            public void setSpecsName(String specsName) {
                this.specsName = specsName;
            }

            public String getStockNum() {
                return stockNum;
            }

            public void setStockNum(String stockNum) {
                this.stockNum = stockNum;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getPutTime() {
                return putTime;
            }

            public void setPutTime(String putTime) {
                this.putTime = putTime;
            }

            public String getIndexFlag() {
                return indexFlag;
            }

            public void setIndexFlag(String indexFlag) {
                this.indexFlag = indexFlag;
            }

            public String getGoodsStatus() {
                return goodsStatus;
            }

            public void setGoodsStatus(String goodsStatus) {
                this.goodsStatus = goodsStatus;
            }
        }
    }
}

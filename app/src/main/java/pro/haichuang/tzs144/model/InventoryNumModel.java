package pro.haichuang.tzs144.model;

import java.util.List;

public class InventoryNumModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"sellOutNum":12,"list":[{"stockNum":-1,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":72,"goodsName":"测试商品"},{"stockNum":89,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":73,"goodsName":"回收材料"},{"stockNum":95,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":74,"goodsName":"测试商品-2"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":75,"goodsName":"陈克明桃花液空桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":76,"goodsName":"明爽桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_77_1611561277492.jpg","id":77,"goodsName":"测试商品0125"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":78,"goodsName":"老版桃花液桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_80_1611563643446.png","id":80,"goodsName":"测试图片显示"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":81,"goodsName":"其他通用桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_82_1611629768553.png","id":82,"goodsName":"桃花液天然矿泉水"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_83_1611631134802.png","id":83,"goodsName":"桃花液珍贵天然矿泉水"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_84_1611631668278.jpg","id":84,"goodsName":"测试图片显示0126"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_85_1611633198297.jpg","id":85,"goodsName":"图片大小显示"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_86_1611633713470.png","id":86,"goodsName":"明爽泉天然矿泉水"}],"sellNum":2,"allNum":14}
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
         * sellOutNum : 12
         * list : [{"stockNum":-1,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":72,"goodsName":"测试商品"},{"stockNum":89,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":73,"goodsName":"回收材料"},{"stockNum":95,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":74,"goodsName":"测试商品-2"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":75,"goodsName":"陈克明桃花液空桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":76,"goodsName":"明爽桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_77_1611561277492.jpg","id":77,"goodsName":"测试商品0125"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":78,"goodsName":"老版桃花液桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_80_1611563643446.png","id":80,"goodsName":"测试图片显示"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/","id":81,"goodsName":"其他通用桶"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_82_1611629768553.png","id":82,"goodsName":"桃花液天然矿泉水"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_83_1611631134802.png","id":83,"goodsName":"桃花液珍贵天然矿泉水"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_84_1611631668278.jpg","id":84,"goodsName":"测试图片显示0126"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_85_1611633198297.jpg","id":85,"goodsName":"图片大小显示"},{"stockNum":0,"salesNum":0,"img":"https://api-tzs144.haichuang.pro/upload/images/GOODS_86_1611633713470.png","id":86,"goodsName":"明爽泉天然矿泉水"}]
         * sellNum : 2
         * allNum : 14
         */

        private int sellOutNum;
        private int sellNum;
        private int allNum;
        private List<ListBean> list;

        public int getSellOutNum() {
            return sellOutNum;
        }

        public void setSellOutNum(int sellOutNum) {
            this.sellOutNum = sellOutNum;
        }

        public int getSellNum() {
            return sellNum;
        }

        public void setSellNum(int sellNum) {
            this.sellNum = sellNum;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * stockNum : -1
             * salesNum : 0
             * img : https://api-tzs144.haichuang.pro/upload/images/
             * id : 72
             * goodsName : 测试商品
             */

            private int stockNum;
            private int salesNum;
            private String img;
            private int id;
            private String goodsName;
            private String specsName;

            public String getSpecsName() {
                return specsName;
            }

            public void setSpecsName(String specsName) {
                this.specsName = specsName;
            }

            public int getStockNum() {
                return stockNum;
            }

            public void setStockNum(int stockNum) {
                this.stockNum = stockNum;
            }

            public int getSalesNum() {
                return salesNum;
            }

            public void setSalesNum(int salesNum) {
                this.salesNum = salesNum;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}

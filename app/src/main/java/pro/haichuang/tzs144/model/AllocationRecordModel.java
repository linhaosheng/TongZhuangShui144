package pro.haichuang.tzs144.model;

import java.util.List;

public class AllocationRecordModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","fromStockName":"","toStockName":"","time":"","realName":"","itemList":[{"goodsName":"","num":""}]}]
     */

    private int result;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id :
         * fromStockName :
         * toStockName :
         * time :
         * realName :
         * itemList : [{"goodsName":"","num":""}]
         */

        private String id;
        private String fromStockName;
        private String toStockName;
        private String time;
        private String realName;
        private List<ItemListBean> itemList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFromStockName() {
            return fromStockName;
        }

        public void setFromStockName(String fromStockName) {
            this.fromStockName = fromStockName;
        }

        public String getToStockName() {
            return toStockName;
        }

        public void setToStockName(String toStockName) {
            this.toStockName = toStockName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * goodsName :
             * num :
             */

            private String goodsName;
            private String num;

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
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

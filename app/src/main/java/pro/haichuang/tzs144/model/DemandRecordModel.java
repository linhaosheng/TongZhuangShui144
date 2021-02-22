package pro.haichuang.tzs144.model;

import java.util.List;

public class DemandRecordModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"stockMain":"","time":"","realName":"","itemList":[{"name":"","num":""}]}]
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
         * stockMain :
         * time :
         * realName :
         * itemList : [{"name":"","num":""}]
         */

        private String stockMain;
        private String time;
        private String realName;
        private List<ItemListBean> itemList;

        public String getStockMain() {
            return stockMain;
        }

        public void setStockMain(String stockMain) {
            this.stockMain = stockMain;
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
             * name :
             * num :
             */

            private String name;
            private String num;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

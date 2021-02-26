package pro.haichuang.tzs144.model;

import java.util.List;

public class StockMainModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":21,"stockName":"桃花仑门店","stockVolume":"","saleType":"全部","staffId":30,"isError":0,"creator":1,"createTime":"2021-01-24T10:20:34.000+0000","isDelete":0},{"id":20,"stockName":"环保路门店","stockVolume":"","saleType":"全部","staffId":29,"isError":0,"creator":1,"createTime":"2021-01-24T10:20:11.000+0000","isDelete":0},{"id":19,"stockName":"湘HAG630","stockVolume":"80","saleType":"配送","staffId":31,"isError":0,"creator":1,"createTime":"2021-01-24T08:30:54.000+0000","isDelete":0},{"id":18,"stockName":"桥北门店","stockVolume":"","saleType":"全部","staffId":28,"isError":0,"creator":1,"createTime":"2021-01-24T08:29:30.000+0000","isDelete":0},{"id":17,"stockName":"成都门店","stockVolume":"100","saleType":"全部","staffId":1,"isError":0,"creator":1,"createTime":"2021-01-14T05:53:49.000+0000","isDelete":0}]
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
         * id : 21
         * stockName : 桃花仑门店
         * stockVolume :
         * saleType : 全部
         * staffId : 30
         * isError : 0
         * creator : 1
         * createTime : 2021-01-24T10:20:34.000+0000
         * isDelete : 0
         */

        private int id;
        private String stockName;
        private String stockVolume;
        private String saleType;
        private int staffId;
        private int isError;
        private int creator;
        private String createTime;
        private int isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStockName() {
            return stockName;
        }

        public void setStockName(String stockName) {
            this.stockName = stockName;
        }

        public String getStockVolume() {
            return stockVolume;
        }

        public void setStockVolume(String stockVolume) {
            this.stockVolume = stockVolume;
        }

        public String getSaleType() {
            return saleType;
        }

        public void setSaleType(String saleType) {
            this.saleType = saleType;
        }

        public int getStaffId() {
            return staffId;
        }

        public void setStaffId(int staffId) {
            this.staffId = staffId;
        }

        public int getIsError() {
            return isError;
        }

        public void setIsError(int isError) {
            this.isError = isError;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }
    }
}

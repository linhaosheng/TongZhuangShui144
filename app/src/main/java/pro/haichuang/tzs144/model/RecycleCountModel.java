package pro.haichuang.tzs144.model;

import java.util.List;

public class RecycleCountModel {


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
            private String ids;
            private String goods_name;
            private String num;
            private String storehouse_name;
            private String stock_name;
            private String minTime;
            private String maxTime;

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getStorehouse_name() {
                return storehouse_name;
            }

            public void setStorehouse_name(String storehouse_name) {
                this.storehouse_name = storehouse_name;
            }

            public String getStock_name() {
                return stock_name;
            }

            public void setStock_name(String stock_name) {
                this.stock_name = stock_name;
            }

            public String getMinTime() {
                return minTime;
            }

            public void setMinTime(String minTime) {
                this.minTime = minTime;
            }

            public String getMaxTime() {
                return maxTime;
            }

            public void setMaxTime(String maxTime) {
                this.maxTime = maxTime;
            }
        }
    }
}

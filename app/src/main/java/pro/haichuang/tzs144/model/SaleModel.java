package pro.haichuang.tzs144.model;

import java.util.List;

public class SaleModel {


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
            private String goods_name;
            private String htNum;
            private String hsNum;
            private String qsNum;
            private String saleNum;


            public String getQsNum() {
                return qsNum;
            }

            public void setQsNum(String qsNum) {
                this.qsNum = qsNum;
            }

            public String getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(String saleNum) {
                this.saleNum = saleNum;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getHtNum() {
                return htNum;
            }

            public void setHtNum(String htNum) {
                this.htNum = htNum;
            }

            public String getHsNum() {
                return hsNum;
            }

            public void setHsNum(String hsNum) {
                this.hsNum = hsNum;
            }
        }
    }
}

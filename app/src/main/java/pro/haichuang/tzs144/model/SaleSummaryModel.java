package pro.haichuang.tzs144.model;

import java.util.List;

public class SaleSummaryModel {


    /**
     * result : 1
     * message : 搜索成功!
     * data : [{"address":"四川省成都市武侯区桂溪街道四川省匹客办公家具有限公司环球中心E3(新世纪环球中心)","phone":"13551262123","latitude":0,"name":"测试","addressName":"测试地址","id":18,"type":"协议客户","addressId":15,"longitude":0}]
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

        private int jnNum;
        private int qnNum;
        private String goodsName;
        private int goodsId;
        private int nnNum;

        public int getJnNum() {
            return jnNum;
        }

        public void setJnNum(int jnNum) {
            this.jnNum = jnNum;
        }

        public int getQnNum() {
            return qnNum;
        }

        public void setQnNum(int qnNum) {
            this.qnNum = qnNum;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getNnNum() {
            return nnNum;
        }

        public void setNnNum(int nnNum) {
            this.nnNum = nnNum;
        }
    }
}

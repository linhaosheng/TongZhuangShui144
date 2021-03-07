package pro.haichuang.tzs144.model;

import java.util.List;

public class DepositModel {


    /**
     * result : 1
     * message : 查询成功！
     * data : [{"id":65,"depositBookId":3,"yjNo":"1000154","yjStatus":0},{"id":243,"depositBookId":4,"yjNo":"03071448154","yjStatus":0}]
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
         * id : 65
         * depositBookId : 3
         * yjNo : 1000154
         * yjStatus : 0
         */

        private int id;
        private int depositBookId;
        private String yjNo;
        private int yjStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDepositBookId() {
            return depositBookId;
        }

        public void setDepositBookId(int depositBookId) {
            this.depositBookId = depositBookId;
        }

        public String getYjNo() {
            return yjNo;
        }

        public void setYjNo(String yjNo) {
            this.yjNo = yjNo;
        }

        public int getYjStatus() {
            return yjStatus;
        }

        public void setYjStatus(int yjStatus) {
            this.yjStatus = yjStatus;
        }
    }
}

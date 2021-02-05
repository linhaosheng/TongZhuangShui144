package pro.haichuang.tzs144.model;

import java.util.List;

public class DepositManagerModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":5,"depNo":"","startNo":"","endNo":"","createTime":""}]
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
         * id : 5
         * depNo :
         * startNo :
         * endNo :
         * createTime :
         */

        private int id;
        private String depNo;
        private String startNo;
        private String endNo;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDepNo() {
            return depNo;
        }

        public void setDepNo(String depNo) {
            this.depNo = depNo;
        }

        public String getStartNo() {
            return startNo;
        }

        public void setStartNo(String startNo) {
            this.startNo = startNo;
        }

        public String getEndNo() {
            return endNo;
        }

        public void setEndNo(String endNo) {
            this.endNo = endNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}

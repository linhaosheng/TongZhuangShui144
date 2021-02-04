package pro.haichuang.tzs144.model;

import java.util.List;

public class ClientTypeModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"name":"协议客户","id":1},{"name":"终端客户","id":2},{"name":"经销商客户","id":3}]
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
         * name : 协议客户
         * id : 1
         */
        private boolean isCheck;
        private String name;
        private int id;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

package pro.haichuang.tzs144.model;

import java.util.List;

public class StaffModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"name":"管理员","id":1},{"name":"丁艳","id":27},{"name":"刘源","id":28},{"name":"刘玉龙","id":29},{"name":"于凯","id":30},{"name":"陈健康","id":31}]
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
         * name : 管理员
         * id : 1
         */

        private String name;
        private int id;

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

package pro.haichuang.tzs144.model;

import java.util.List;

public class AreaModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"name":"高新区","id":41},{"name":"城院","id":40},{"name":"七里桥","id":39},{"name":"万达","id":38},{"name":"桃花仑","id":37},{"name":"桥北","id":36},{"name":"123","id":35}]
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
         * name : 高新区
         * id : 41
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

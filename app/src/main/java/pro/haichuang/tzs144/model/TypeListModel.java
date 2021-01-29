package pro.haichuang.tzs144.model;

import java.util.List;

public class TypeListModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"name":"桶装水","id":63},{"name":"瓶装水","id":64},{"name":"空桶","id":65},{"name":"水票","id":66},{"name":"饮水机","id":87},{"name":"压水泵","id":88}]
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
         * name : 桶装水
         * id : 63
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

package pro.haichuang.tzs144.model;

import java.util.List;

public class ShopModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"specs":"16.9L","price":20,"name":"日之泉天然矿泉水","id":88},{"specs":"16.9L","price":18,"name":"山外人家天然矿泉水","id":87},{"specs":"18.9L","price":10,"name":"明爽泉天然矿泉水","id":86},{"specs":"16.9L","price":12,"name":"桃花液珍贵天然矿泉水","id":83},{"specs":"16.9L","price":10,"name":"桃花液天然矿泉水","id":82},{"specs":"18.9L","price":15,"name":"测试图片显示","id":80},{"specs":"18.9L","price":15,"name":"测试商品0125","id":77},{"specs":"18.9L","price":1,"name":"测试商品-2","id":74},{"specs":"18.9L","price":10,"name":"测试商品","id":72}]
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
         * specs : 16.9L
         * price : 20.0
         * name : 日之泉天然矿泉水
         * id : 88
         */

        private boolean check;
        private String specs;
        private double price;
        private String name;
        private int id;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

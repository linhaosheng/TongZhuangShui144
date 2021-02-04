package pro.haichuang.tzs144.model;

import java.util.List;

public class SaleDataModel {


    /**
     * result : 1
     * message : 搜索成功!
     * data : [{"address":"四川省成都市武侯区桂溪街道四川省匹客办公家具有限公司环球中心E3(新世纪环球中心)","phone":"13551262123","name":"测试","addressName":"测试地址","id":18}]
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
         * address : 四川省成都市武侯区桂溪街道四川省匹客办公家具有限公司环球中心E3(新世纪环球中心)
         * phone : 13551262123
         * name : 测试
         * addressName : 测试地址
         * id : 18
         */

        private String address;
        private String phone;
        private String name;
        private String addressName;
        private int id;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

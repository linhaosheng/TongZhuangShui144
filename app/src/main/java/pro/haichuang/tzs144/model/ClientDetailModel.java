package pro.haichuang.tzs144.model;

import java.util.List;

public class ClientDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"area":"七里桥-银城市场","customerType":"终端客户","business":"","phone":"17398765465","addressList":[{"address":"成都市双流区 骑龙-地铁站","addressName":"成都","id":20}],"inviter":"管理员","id":26,"customerName":"李先生","contacts":"木子李","maintainList":[]}
     */

    private int result;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 七里桥-银城市场
         * customerType : 终端客户
         * business :
         * phone : 17398765465
         * addressList : [{"address":"成都市双流区 骑龙-地铁站","addressName":"成都","id":20}]
         * inviter : 管理员
         * id : 26
         * customerName : 李先生
         * contacts : 木子李
         * maintainList : []
         */

        private String area;
        private String customerType;
        private String business;
        private String phone;
        private String inviter;
        private int id;
        private String customerName;
        private String contacts;
        private List<AddressListBean> addressList;
        private List<MaintainListBean> maintainList;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public List<AddressListBean> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressListBean> addressList) {
            this.addressList = addressList;
        }

        public List<MaintainListBean> getMaintainList() {
            return maintainList;
        }

        public void setMaintainList(List<MaintainListBean> maintainList) {
            this.maintainList = maintainList;
        }

        public static class AddressListBean {
            /**
             * address : 成都市双流区 骑龙-地铁站
             * addressName : 成都
             * id : 20
             */

            private String address;
            private String addressName;
            private int id;
            private String latitude;
            private String longitude;
            private boolean upadteAddress;
            private boolean isEdit;
            private boolean addAddress;
            private String newAddressName;
            private String newAddress;

            public String getNewAddressName() {
                return newAddressName;
            }

            public void setNewAddressName(String newAddressName) {
                this.newAddressName = newAddressName;
            }

            public String getNewAddress() {
                return newAddress;
            }

            public void setNewAddress(String newAddress) {
                this.newAddress = newAddress;
            }

            public boolean isAddAddress() {
                return addAddress;
            }

            public void setAddAddress(boolean addAddress) {
                this.addAddress = addAddress;
            }

            public boolean isEdit() {
                return isEdit;
            }

            public void setEdit(boolean edit) {
                isEdit = edit;
            }

            public boolean isUpadteAddress() {
                return upadteAddress;
            }

            public void setUpadteAddress(boolean upadteAddress) {
                this.upadteAddress = upadteAddress;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

        public static class MaintainListBean {
            /**
             * id :
             * time :
             * maintainInfo :
             */

            private int id;
            private String time;
            private String maintainInfo;
            private boolean isEdit;


            public boolean isEdit() {
                return isEdit;
            }

            public void setEdit(boolean edit) {
                isEdit = edit;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMaintainInfo() {
                return maintainInfo;
            }

            public void setMaintainInfo(String maintainInfo) {
                this.maintainInfo = maintainInfo;
            }
        }
    }
}

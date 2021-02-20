package pro.haichuang.tzs144.model;

import java.util.List;

public class ClientDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","customerName":"","customerType":"","contacts":"","phone":"","area":"","inviter":"","business":"","addressList":[{"id":"","addressName":"","address":""}],"maintainList":[{"id":"","time":"","maintainInfo":""}]}]
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
         * id :
         * customerName :
         * customerType :
         * contacts :
         * phone :
         * area :
         * inviter :
         * business :
         * addressList : [{"id":"","addressName":"","address":""}]
         * maintainList : [{"id":"","time":"","maintainInfo":""}]
         */

        private String id;
        private String customerName;
        private String customerType;
        private String contacts;
        private String phone;
        private String area;
        private String inviter;
        private String business;
        private List<AddressListBean> addressList;
        private List<MaintainListBean> maintainList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
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
             * id :
             * addressName :
             * address :
             */

            private String id;
            private String addressName;
            private String address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAddressName() {
                return addressName;
            }

            public void setAddressName(String addressName) {
                this.addressName = addressName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public static class MaintainListBean {
            /**
             * id :
             * time :
             * maintainInfo :
             */

            private String id;
            private String time;
            private String maintainInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

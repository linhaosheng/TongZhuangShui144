package pro.haichuang.tzs144.model;

import java.util.List;

public class AddClientModel {


    /**
     * customerTypeId : 0
     * honeycombId : 0
     * honeycombGridId : 0
     * customerName :
     * contacts :
     * phone :
     * isMonopoly : true
     * inviterId : 0
     * businessId : 0
     * addressList : [{"addressName":"","address":"","longitude":"","latitude":""}]
     */

    private int customerTypeId;
    private int honeycombId;
    private int honeycombGridId;
    private String customerName;
    private String contacts;
    private String phone;
    private int isMonopoly;
    private int inviterId;
    private int businessId;
    private List<AddressListBean> addressList;

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getHoneycombId() {
        return honeycombId;
    }

    public void setHoneycombId(int honeycombId) {
        this.honeycombId = honeycombId;
    }

    public int getHoneycombGridId() {
        return honeycombGridId;
    }

    public void setHoneycombGridId(int honeycombGridId) {
        this.honeycombGridId = honeycombGridId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int isIsMonopoly() {
        return isMonopoly;
    }

    public void setIsMonopoly(int isMonopoly) {
        this.isMonopoly = isMonopoly;
    }

    public int getInviterId() {
        return inviterId;
    }

    public void setInviterId(int inviterId) {
        this.inviterId = inviterId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public List<AddressListBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressListBean> addressList) {
        this.addressList = addressList;
    }

    public static class AddressListBean {
        /**
         * addressName :
         * address :
         * longitude :
         * latitude :
         */

        private String addressName;
        private String address;
        private String longitude;
        private String latitude;

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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}

package pro.haichuang.tzs144.model;

import java.util.List;

public class WithDrawalOrderModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","yjNo":"","bookNo":"","khName":"","goodsName":"","num":"","price":"","totalPrice":"","type":"","time":"","createName":"","returnName":"","returnTime":"","status":"","isAdd":false}]
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
         * yjNo :
         * bookNo :
         * khName :
         * goodsName :
         * num :
         * price :
         * totalPrice :
         * type :
         * time :
         * createName :
         * returnName :
         * returnTime :
         * status :
         * isAdd : false
         */

        private String id;
        private String yjNo;
        private String bookNo;
        private String khName;
        private String goodsName;
        private String num;
        private String price;
        private String totalPrice;
        private int type;
        private String time;
        private String createName;
        private String returnName;
        private String returnTime;
        private int khId;
        private String addressName;
        private String address;
        private String khPhone;
        private int status;
        private boolean isAdd;
        private boolean checked;
        private String cancelTime;

        public int getKhId() {
            return khId;
        }

        public void setKhId(int khId) {
            this.khId = khId;
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

        public String getKhPhone() {
            return khPhone;
        }

        public void setKhPhone(String khPhone) {
            this.khPhone = khPhone;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYjNo() {
            return yjNo;
        }

        public void setYjNo(String yjNo) {
            this.yjNo = yjNo;
        }

        public String getBookNo() {
            return bookNo;
        }

        public void setBookNo(String bookNo) {
            this.bookNo = bookNo;
        }

        public String getKhName() {
            return khName;
        }

        public void setKhName(String khName) {
            this.khName = khName;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getReturnName() {
            return returnName;
        }

        public void setReturnName(String returnName) {
            this.returnName = returnName;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isIsAdd() {
            return isAdd;
        }

        public void setIsAdd(boolean isAdd) {
            this.isAdd = isAdd;
        }
    }
}

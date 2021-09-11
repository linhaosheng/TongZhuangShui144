package pro.haichuang.tzs144.model;

import android.text.TextUtils;

import java.util.List;

public class WithDrawalOrderModel {


    private Integer result;
    private String message;
    private List<DataBean> data;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
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
        private String bookNo;
        private Integer totalPrice;
        private Integer num;
        private String yjNo;
        private String returnName;
        private Integer isAdd;
        private Integer type;
        private String returnTime;
        private Integer price;
        private Integer id;
        private String time;
        private String khName;
        private String goodsName;
        private String createName;
        private Integer status;

        private String addressName;
        private String address;
        private String khPhone;
        private int khId;
        private boolean checked;
        private String cancelTime;
        private String returnPrice;
        private String returnCount;
        private String mdPrice;

        public Integer getIsAdd() {
            return isAdd;
        }

        public String getReturnPrice() {
            if (TextUtils.isEmpty(returnPrice)){
                return "";
            }
            return returnPrice;
        }

        public void setReturnPrice(String returnPrice) {
            this.returnPrice = returnPrice;
        }

        public String getReturnCount() {
            if (TextUtils.isEmpty(returnCount)){
                return "";
            }
            return returnCount;
        }

        public void setReturnCount(String returnCount) {
            this.returnCount = returnCount;
        }

        public String getMdPrice() {
            if (TextUtils.isEmpty(mdPrice)){
                return "";
            }
            return mdPrice;
        }

        public void setMdPrice(String mdPrice) {
            this.mdPrice = mdPrice;
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

        public int getKhId() {
            return khId;
        }

        public void setKhId(int khId) {
            this.khId = khId;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getBookNo() {
            return bookNo;
        }

        public void setBookNo(String bookNo) {
            this.bookNo = bookNo;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public String getYjNo() {
            return yjNo;
        }

        public void setYjNo(String yjNo) {
            this.yjNo = yjNo;
        }

        public String getReturnName() {
            return returnName;
        }

        public void setReturnName(String returnName) {
            this.returnName = returnName;
        }

        public boolean isAdd() {
            if (isAdd==1){
                return true;
            }
            return false;
        }

        public void setIsAdd(Integer isAdd) {
            this.isAdd = (Integer) isAdd;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}

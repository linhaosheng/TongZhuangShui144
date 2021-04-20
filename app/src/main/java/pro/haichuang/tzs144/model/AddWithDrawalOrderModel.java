package pro.haichuang.tzs144.model;

import java.util.List;

public class AddWithDrawalOrderModel {


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
        private Boolean isAdd;
        private Integer type;
        private String price;
        private Integer id;
        private String time;
        private String khName;
        private String goodsName;
        private String createName;
        private Integer status;

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

        public Boolean getIsAdd() {
            return isAdd;
        }

        public void setIsAdd(Boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

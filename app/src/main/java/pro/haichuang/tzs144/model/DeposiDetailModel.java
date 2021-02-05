package pro.haichuang.tzs144.model;

import java.util.List;

public class DeposiDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : {"yjKyNum":"","yjGoodsNum":"","yjPrice":"","yjTyNum":"","ytTyPrice":"","jtKyNum":"","jtGoodsNum":"","jtPrice":"","jtTyNum":"","jtTyPrice":"","zqKyNum":"","zqGoodsNum":"","zqPrice":"","zqTyNum":"","zqTyPrice":"","list":[{"id":"","yjNo":"","bookNo":"","khName":"","goodsName":"","num":"","price":"","totalPrice":"","type":"","time":"","createName":"","returnName":"","returnTime":"","status":""}]}
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
         * yjKyNum :
         * yjGoodsNum :
         * yjPrice :
         * yjTyNum :
         * ytTyPrice :
         * jtKyNum :
         * jtGoodsNum :
         * jtPrice :
         * jtTyNum :
         * jtTyPrice :
         * zqKyNum :
         * zqGoodsNum :
         * zqPrice :
         * zqTyNum :
         * zqTyPrice :
         * list : [{"id":"","yjNo":"","bookNo":"","khName":"","goodsName":"","num":"","price":"","totalPrice":"","type":"","time":"","createName":"","returnName":"","returnTime":"","status":""}]
         */

        private String yjKyNum;
        private String yjGoodsNum;
        private String yjPrice;
        private String yjTyNum;
        private String ytTyPrice;
        private String jtKyNum;
        private String jtGoodsNum;
        private String jtPrice;
        private String jtTyNum;
        private String jtTyPrice;
        private String zqKyNum;
        private String zqGoodsNum;
        private String zqPrice;
        private String zqTyNum;
        private String zqTyPrice;
        private List<ListBean> list;

        public String getYjKyNum() {
            return yjKyNum;
        }

        public void setYjKyNum(String yjKyNum) {
            this.yjKyNum = yjKyNum;
        }

        public String getYjGoodsNum() {
            return yjGoodsNum;
        }

        public void setYjGoodsNum(String yjGoodsNum) {
            this.yjGoodsNum = yjGoodsNum;
        }

        public String getYjPrice() {
            return yjPrice;
        }

        public void setYjPrice(String yjPrice) {
            this.yjPrice = yjPrice;
        }

        public String getYjTyNum() {
            return yjTyNum;
        }

        public void setYjTyNum(String yjTyNum) {
            this.yjTyNum = yjTyNum;
        }

        public String getYtTyPrice() {
            return ytTyPrice;
        }

        public void setYtTyPrice(String ytTyPrice) {
            this.ytTyPrice = ytTyPrice;
        }

        public String getJtKyNum() {
            return jtKyNum;
        }

        public void setJtKyNum(String jtKyNum) {
            this.jtKyNum = jtKyNum;
        }

        public String getJtGoodsNum() {
            return jtGoodsNum;
        }

        public void setJtGoodsNum(String jtGoodsNum) {
            this.jtGoodsNum = jtGoodsNum;
        }

        public String getJtPrice() {
            return jtPrice;
        }

        public void setJtPrice(String jtPrice) {
            this.jtPrice = jtPrice;
        }

        public String getJtTyNum() {
            return jtTyNum;
        }

        public void setJtTyNum(String jtTyNum) {
            this.jtTyNum = jtTyNum;
        }

        public String getJtTyPrice() {
            return jtTyPrice;
        }

        public void setJtTyPrice(String jtTyPrice) {
            this.jtTyPrice = jtTyPrice;
        }

        public String getZqKyNum() {
            return zqKyNum;
        }

        public void setZqKyNum(String zqKyNum) {
            this.zqKyNum = zqKyNum;
        }

        public String getZqGoodsNum() {
            return zqGoodsNum;
        }

        public void setZqGoodsNum(String zqGoodsNum) {
            this.zqGoodsNum = zqGoodsNum;
        }

        public String getZqPrice() {
            return zqPrice;
        }

        public void setZqPrice(String zqPrice) {
            this.zqPrice = zqPrice;
        }

        public String getZqTyNum() {
            return zqTyNum;
        }

        public void setZqTyNum(String zqTyNum) {
            this.zqTyNum = zqTyNum;
        }

        public String getZqTyPrice() {
            return zqTyPrice;
        }

        public void setZqTyPrice(String zqTyPrice) {
            this.zqTyPrice = zqTyPrice;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
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
             */

            private String id;
            private String yjNo;
            private String bookNo;
            private String khName;
            private String goodsName;
            private String num;
            private String price;
            private String totalPrice;
            private String type;
            private String time;
            private String createName;
            private String returnName;
            private String returnTime;
            private String status;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}

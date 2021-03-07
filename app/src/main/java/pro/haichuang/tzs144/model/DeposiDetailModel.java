package pro.haichuang.tzs144.model;

import java.util.List;

public class DeposiDetailModel {


    /**
     * result : 1
     * message : 查询成功！
     * data : {"zqKyNum":0,"yjPrice":0,"jtGoodsNum":0,"jtTyNum":0,"zqTyNum":0,"zqTyPrice":0,"ytTyPrice":0,"yjKyNum":0,"list":[],"jtKyNum":0,"yjGoodsNum":0,"jtTyPrice":0,"zqPrice":0,"yjTyNum":0,"zqGoodsNum":0,"jtPrice":0}
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
         * zqKyNum : 0
         * yjPrice : 0.0
         * jtGoodsNum : 0
         * jtTyNum : 0
         * zqTyNum : 0
         * zqTyPrice : 0.0
         * ytTyPrice : 0.0
         * yjKyNum : 0
         * list : []
         * jtKyNum : 0
         * yjGoodsNum : 0
         * jtTyPrice : 0.0
         * zqPrice : 0.0
         * yjTyNum : 0
         * zqGoodsNum : 0
         * jtPrice : 0.0
         */

        private int zqKyNum;
        private double yjPrice;
        private int jtGoodsNum;
        private int jtTyNum;
        private int zqTyNum;
        private double zqTyPrice;
        private double ytTyPrice;
        private int yjKyNum;
        private int jtKyNum;
        private int yjGoodsNum;
        private double jtTyPrice;
        private double zqPrice;
        private int yjTyNum;
        private int zqGoodsNum;
        private double jtPrice;

        private List<ListBean> list;

        public int getZqKyNum() {
            return zqKyNum;
        }

        public void setZqKyNum(int zqKyNum) {
            this.zqKyNum = zqKyNum;
        }

        public double getYjPrice() {
            return yjPrice;
        }

        public void setYjPrice(double yjPrice) {
            this.yjPrice = yjPrice;
        }

        public int getJtGoodsNum() {
            return jtGoodsNum;
        }

        public void setJtGoodsNum(int jtGoodsNum) {
            this.jtGoodsNum = jtGoodsNum;
        }

        public int getJtTyNum() {
            return jtTyNum;
        }

        public void setJtTyNum(int jtTyNum) {
            this.jtTyNum = jtTyNum;
        }

        public int getZqTyNum() {
            return zqTyNum;
        }

        public void setZqTyNum(int zqTyNum) {
            this.zqTyNum = zqTyNum;
        }

        public double getZqTyPrice() {
            return zqTyPrice;
        }

        public void setZqTyPrice(double zqTyPrice) {
            this.zqTyPrice = zqTyPrice;
        }

        public double getYtTyPrice() {
            return ytTyPrice;
        }

        public void setYtTyPrice(double ytTyPrice) {
            this.ytTyPrice = ytTyPrice;
        }

        public int getYjKyNum() {
            return yjKyNum;
        }

        public void setYjKyNum(int yjKyNum) {
            this.yjKyNum = yjKyNum;
        }

        public int getJtKyNum() {
            return jtKyNum;
        }

        public void setJtKyNum(int jtKyNum) {
            this.jtKyNum = jtKyNum;
        }

        public int getYjGoodsNum() {
            return yjGoodsNum;
        }

        public void setYjGoodsNum(int yjGoodsNum) {
            this.yjGoodsNum = yjGoodsNum;
        }

        public double getJtTyPrice() {
            return jtTyPrice;
        }

        public void setJtTyPrice(double jtTyPrice) {
            this.jtTyPrice = jtTyPrice;
        }

        public double getZqPrice() {
            return zqPrice;
        }

        public void setZqPrice(double zqPrice) {
            this.zqPrice = zqPrice;
        }

        public int getYjTyNum() {
            return yjTyNum;
        }

        public void setYjTyNum(int yjTyNum) {
            this.yjTyNum = yjTyNum;
        }

        public int getZqGoodsNum() {
            return zqGoodsNum;
        }

        public void setZqGoodsNum(int zqGoodsNum) {
            this.zqGoodsNum = zqGoodsNum;
        }

        public double getJtPrice() {
            return jtPrice;
        }

        public void setJtPrice(double jtPrice) {
            this.jtPrice = jtPrice;
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

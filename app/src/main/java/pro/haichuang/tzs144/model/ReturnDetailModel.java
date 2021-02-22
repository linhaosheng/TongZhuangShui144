package pro.haichuang.tzs144.model;

import java.util.List;

public class ReturnDetailModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"id":"","oddNumber":"","goodsName":"","outNum":"","createName":"","createTime":"","mainUserName":"","materialList":[{"name":"","num":""}]}]
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
         * oddNumber :
         * goodsName :
         * outNum :
         * createName :
         * createTime :
         * mainUserName :
         * materialList : [{"name":"","num":""}]
         */

        private String id;
        private String oddNumber;
        private String goodsName;
        private String outNum;
        private String createName;
        private String createTime;
        private String mainUserName;
        private List<MaterialListBean> materialList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOddNumber() {
            return oddNumber;
        }

        public void setOddNumber(String oddNumber) {
            this.oddNumber = oddNumber;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getOutNum() {
            return outNum;
        }

        public void setOutNum(String outNum) {
            this.outNum = outNum;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMainUserName() {
            return mainUserName;
        }

        public void setMainUserName(String mainUserName) {
            this.mainUserName = mainUserName;
        }

        public List<MaterialListBean> getMaterialList() {
            return materialList;
        }

        public void setMaterialList(List<MaterialListBean> materialList) {
            this.materialList = materialList;
        }

        public static class MaterialListBean {
            /**
             * name :
             * num :
             */

            private String name;
            private String num;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}

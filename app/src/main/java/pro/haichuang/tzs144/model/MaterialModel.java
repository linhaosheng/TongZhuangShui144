package pro.haichuang.tzs144.model;

import android.util.Log;

import java.util.List;

public class MaterialModel {


    /**
     * result : 1
     * message : 获取成功!
     * data : [{"name":"老版桃花液桶","id":78}]
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
         * name : 老版桃花液桶
         * id : 78
         */

        private int num;
        private String name;
        private int id;
        private int materialId;


        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            Log.i("init===","id==="+id);
            this.id = id;
            this.materialId = id;
        }
    }
}

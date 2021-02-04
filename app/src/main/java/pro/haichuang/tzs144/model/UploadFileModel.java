package pro.haichuang.tzs144.model;

public class UploadFileModel {


    /**
     * result : 1
     * message : 上传成功!
     * data : {"fileName":"TEMP_1612258349073.jpg","fileUrl":"https://api-tzs144.haichuang.pro/upload/TEMP_1612258349073.jpg"}
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
         * fileName : TEMP_1612258349073.jpg
         * fileUrl : https://api-tzs144.haichuang.pro/upload/TEMP_1612258349073.jpg
         */

        private String key;
        private String fileName;
        private String fileUrl;


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}

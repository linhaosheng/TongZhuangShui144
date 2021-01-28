package pro.haichuang.tzs144.model;

public class LoginModel {


    /**
     * result : 1
     * message : 登录成功!
     * data : {"verification":""}
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
         * verification :
         */

        private String verification;

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }
    }
}

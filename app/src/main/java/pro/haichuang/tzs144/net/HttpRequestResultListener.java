package pro.haichuang.tzs144.net;

public interface HttpRequestResultListener {

    void start();
    void success(String result);
    void error(String error);
}

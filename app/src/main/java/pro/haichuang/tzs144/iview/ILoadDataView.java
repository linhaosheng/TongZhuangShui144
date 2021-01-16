package pro.haichuang.tzs144.iview;

public interface ILoadDataView<T> {

    void startLoad();
    void successLoad(T data);
    void errorLoad(String error);
}

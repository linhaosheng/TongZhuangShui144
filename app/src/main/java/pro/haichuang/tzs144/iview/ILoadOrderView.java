package pro.haichuang.tzs144.iview;

public interface ILoadOrderView<T> {

    void startLoadOrder();
    void successLoadOrder(T data);
    void errorLoadOrder(String error);
}

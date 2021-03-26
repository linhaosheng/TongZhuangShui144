package pro.haichuang.tzs144.model;

public class RealAccountEvent {

    public final AccountOrderModel dataBean;
    public final int type;

    public RealAccountEvent(AccountOrderModel dataBean,int type){
        this.dataBean = dataBean;
        this.type = type;
    }
}

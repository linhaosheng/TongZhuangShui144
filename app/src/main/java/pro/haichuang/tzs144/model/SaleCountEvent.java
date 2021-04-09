package pro.haichuang.tzs144.model;

public class SaleCountEvent {

    public final SaleModel saleModel;
    public final int status;
    public final int type;

    public SaleCountEvent(SaleModel saleModel, int status,int type){
        this.saleModel = saleModel;
        this.status = status;
        this.type = type;
    }
}

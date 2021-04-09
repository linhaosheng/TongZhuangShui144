package pro.haichuang.tzs144.model;

public class CustodyEvent {

    public final CustodyModel custodyModel;
    public final int status;

    public CustodyEvent(CustodyModel custodyModel,int status){
        this.custodyModel = custodyModel;
        this.status = status;
    }
}

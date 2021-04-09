package pro.haichuang.tzs144.model;

public class IncomeCountEvent {

    public final CustodyModel custodyModel;
    public final int status;

    public IncomeCountEvent(CustodyModel custodyModel, int status){
        this.custodyModel = custodyModel;
        this.status = status;
    }
}

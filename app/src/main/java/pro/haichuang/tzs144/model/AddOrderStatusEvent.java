package pro.haichuang.tzs144.model;

public class AddOrderStatusEvent {

    public final int status;
    public final int id;
    public final int select;

    public AddOrderStatusEvent(int mStatus, int id,int select){
        this.status = mStatus;
        this.id = id;
        this.select = select;
    }
}

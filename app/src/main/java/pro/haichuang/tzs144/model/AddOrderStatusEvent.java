package pro.haichuang.tzs144.model;

public class AddOrderStatusEvent {

    public final int status;
    public final int id;

    public AddOrderStatusEvent(int mStatus, int id){
        this.status = mStatus;
        this.id = id;
    }
}

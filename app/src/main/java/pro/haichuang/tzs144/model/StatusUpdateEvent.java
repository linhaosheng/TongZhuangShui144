package pro.haichuang.tzs144.model;

public class StatusUpdateEvent {

    public final int status;
    public final int type;

    public StatusUpdateEvent(int mStatus, int mType){
        this.status = mStatus;
        this.type = mType;
    }

}

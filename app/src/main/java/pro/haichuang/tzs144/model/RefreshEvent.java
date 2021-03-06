package pro.haichuang.tzs144.model;

public class RefreshEvent {

    public final String status;
    public final int type;

    public RefreshEvent(String mStatus, int mType){
        this.status = mStatus;
        this.type = mType;
    }

}

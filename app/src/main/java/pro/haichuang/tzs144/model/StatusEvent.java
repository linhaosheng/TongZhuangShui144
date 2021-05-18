package pro.haichuang.tzs144.model;

public class StatusEvent {

    public final int status;
    public final int type;
    public String result = "";

    public StatusEvent(int mStatus,int mType){
        this.status = mStatus;
        this.type = mType;
    }

    public void setResult(String result){
        this.result = result;
    }

}

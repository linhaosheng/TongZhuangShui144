package pro.haichuang.tzs144.model;

public class RecycleCountEvent {

    public final RecycleCountModel recycleCountModel;
    public final int status;

    public RecycleCountEvent(RecycleCountModel recycleCountModel, int status){
        this.recycleCountModel = recycleCountModel;
        this.status = status;
    }
}

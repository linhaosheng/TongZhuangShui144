package pro.haichuang.tzs144.model;

public class FileUploadEvent {

    public final int status;
    public final String path;
    public final String key;

    public FileUploadEvent(int status,String path,String key){
        this.status = status;
        this.path = path;
        this.key = key;
    }
}

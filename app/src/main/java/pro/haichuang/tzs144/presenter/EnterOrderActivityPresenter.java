package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;
import android.util.Log;

import java.io.File;
import java.util.Map;

import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;
import rxhttp.wrapper.entity.UpFile;

public class EnterOrderActivityPresenter {


    private IUpLoadFileView iUpLoadFileView;


    public EnterOrderActivityPresenter( IUpLoadFileView iUpLoadFileView){
      this.iUpLoadFileView = iUpLoadFileView;
    }

    /**
     * [直接销售]-录入订单
     */
    public void enterOrder(AddOrderModel addOrderModel){

        Map<String,Object> params = new ArrayMap<>();

        HttpRequestEngine.postRequest(ConfigUrl.ENTER_ORFER, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * 上传文件
     * @param key
     * @param path
     */
    public void uploadFile(String key,String path){

        Log.i("uploadFile====",path);
        UpFile upFile = new UpFile("file",new File(path));

        HttpRequestEngine.uploadFile(ConfigUrl.UPLOAD_FILE, upFile, new HttpRequestResultListener() {
            @Override
            public void start() {
                iUpLoadFileView.startLoad();
            }

            @Override
            public void success(String result) {
                UploadFileModel uploadFileModel = Utils.gsonInstane().fromJson(result, UploadFileModel.class);
                if (uploadFileModel.getResult()!=1){
                    iUpLoadFileView.errorLoad("上传失败");
                }else {
                    uploadFileModel.getData().setKey(key);
                    iUpLoadFileView.successLoad(uploadFileModel.getData());
                }
            }

            @Override
            public void error(String error) {
                iUpLoadFileView.errorLoad(error);
            }
        });
    }
}

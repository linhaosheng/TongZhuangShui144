package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AddMainTainRecordActivityPresenter {

    private ILoadDataView iLoadDataView;

    public AddMainTainRecordActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [客户]新增/编辑维护记录
     * @param id
     * @param customerId
     * @param maintainInfo
     * @param distance
     * @param time
     */
    public void saveMaintainLog(String id,String customerId,String maintainInfo,String distance,String time){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        params.put("customerId",customerId);
        params.put("maintainInfo",maintainInfo);
        params.put("distance",distance);
        params.put("time",time);

        HttpRequestEngine.postRequest(ConfigUrl.SAVE_MAINTAIN_LOG, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
             try {
                 JSONObject jsonObject = new JSONObject(result);
                 if (jsonObject.getInt("result")==1){
                     iLoadDataView.successLoad("success");
                 }else {
                     iLoadDataView.successLoad("fail");
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });



    }
}

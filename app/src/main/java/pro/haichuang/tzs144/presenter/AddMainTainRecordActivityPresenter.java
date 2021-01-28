package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AddMainTainRecordActivityPresenter {

    public AddMainTainRecordActivityPresenter(){

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

            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {

            }
        });



    }
}

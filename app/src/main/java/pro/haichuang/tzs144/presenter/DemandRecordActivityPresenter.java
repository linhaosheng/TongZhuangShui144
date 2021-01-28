package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;

public class DemandRecordActivityPresenter {

    public DemandRecordActivityPresenter(){

    }


    /**
     * [实时库存]查询需求单
     * @param page
     */
    public void findDemands(int page){
        Map<String,Object>params = new ArrayMap<>();
        params.put("page",page);
        params.put("limit", Config.LIMIT);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEMAND, params, new HttpRequestResultListener() {
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

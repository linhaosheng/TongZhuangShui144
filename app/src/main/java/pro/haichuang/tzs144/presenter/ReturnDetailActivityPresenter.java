package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class ReturnDetailActivityPresenter {

    public ReturnDetailActivityPresenter(){

    }

    /**
     * [实时库存]查询取水收捅明细
     * @param startTime
     * @param endTime
     * @param staffId
     * @param page
     */
    public void findQsstLogs(String startTime,String endTime,String staffId,int page ){

        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("staffId",staffId);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_QSST_LOGS, params, new HttpRequestResultListener() {
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

package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class DemandListActivityPresenter {


    public DemandListActivityPresenter(){

    }


    /**
     * [实时库存]录入需求单
     * @param startTime
     * @param endTime
     * @param goodsId
     * @param num
     */
    public void demand(String startTime,String endTime,String goodsId,String num){
        Map<String,Object>map = new ArrayMap<>();
        map.put("goodsId",goodsId);
        map.put("num",num);
        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("goodsList",map);

        HttpRequestEngine.postRequest(ConfigUrl.DEMAND, params, new HttpRequestResultListener() {
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

package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AllocationRecordActivityPresenter {


    public AllocationRecordActivityPresenter(){

    }

    /**
     * [实时库存]查询调拨记录
     * @param page
     */
    public void findAllotGoodsLog(int page){

        Map<String,Object>params = new ArrayMap<>();
        HttpRequestEngine.postRequest(ConfigUrl.FIND_ALLOT_GOODS_LOG, params, new HttpRequestResultListener() {
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

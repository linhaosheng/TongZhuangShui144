package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AllocationActivityPresenter {

    public AllocationActivityPresenter(){

    }

    /**
     * [实时库存]调拨商品列表
     * @param query
     */
    public void findAllotGoods(String query){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ALLOT_GOODS, params, new HttpRequestResultListener() {
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
     * [实时库存]调拨出库
     */
    public void allotGoods(){
        Map<String,Object> params = new ArrayMap<>();
        HttpRequestEngine.postRequest(ConfigUrl.ALLOT_GOODS, params, new HttpRequestResultListener() {
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

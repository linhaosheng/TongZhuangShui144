package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class SaleSearchActivityPresenter {

    public SaleSearchActivityPresenter(){

    }

    /**
     * 客户搜索
     * @param search
     */
    public void search(String search){

        Map<String,Object>params = new ArrayMap<>();
        params.put("search",search);

        HttpRequestEngine.postRequest(ConfigUrl.SEARCH, params, new HttpRequestResultListener() {
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

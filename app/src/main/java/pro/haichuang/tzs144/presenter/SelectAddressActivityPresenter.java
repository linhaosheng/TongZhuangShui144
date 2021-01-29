package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class SelectAddressActivityPresenter {

    public SelectAddressActivityPresenter(){

    }

    /**
     * 客户地址列表
     * @param customerId
     */
    public void findAddress(String customerId){

        Map<String,Object> params = new ArrayMap<>();
        params.put("customerId",customerId);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ADDRESS, params, new HttpRequestResultListener() {
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

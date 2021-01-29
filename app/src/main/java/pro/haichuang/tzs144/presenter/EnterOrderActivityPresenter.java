package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class EnterOrderActivityPresenter {

    public EnterOrderActivityPresenter(){

    }

    /**
     * [直接销售]-录入订单
     */
    public void enterOrder(){

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
}

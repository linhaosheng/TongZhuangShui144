package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class StartDepositActivityPresenter {

    public StartDepositActivityPresenter(){

    }

    /**
     * [押金]提交开押
     */
    public void addDepositInfo(){
        Map<String,Object>params = new ArrayMap<>();

        HttpRequestEngine.postRequest(ConfigUrl.ADD_DEPOSIT_INFO, params, new HttpRequestResultListener() {
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

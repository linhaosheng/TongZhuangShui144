package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AddWithDrawalOrderActivityPresenter {

    public AddWithDrawalOrderActivityPresenter(){

    }

    /**
     * [押金]提交退押
     * @param depositId
     * @param userId
     */
    public void backDeposit(String depositId,String userId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("depositId",depositId);
        params.put("userId",userId);

        HttpRequestEngine.postRequest(ConfigUrl.BACK_DEPOSIT, params, new HttpRequestResultListener() {
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

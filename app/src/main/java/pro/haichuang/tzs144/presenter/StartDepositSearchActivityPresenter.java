package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class StartDepositSearchActivityPresenter {

    public StartDepositSearchActivityPresenter(){

    }

    /**
     * [押金]获取开押列表
     * @param depositBookNum
     */
    public void findDepositBookList(String depositBookNum ){
        Map<String,Object>params = new ArrayMap<>();
        params.put("depositBookNum",depositBookNum);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_LIST, params, new HttpRequestResultListener() {
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

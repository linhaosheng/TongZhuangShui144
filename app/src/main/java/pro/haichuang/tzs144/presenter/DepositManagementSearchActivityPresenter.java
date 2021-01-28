package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class DepositManagementSearchActivityPresenter {

    public DepositManagementSearchActivityPresenter(){

    }

    /**
     * 添加押金本
     * @param number
     * @param numCount
     * @param endNumber
     */
    public void addDepositBook(String number,String numCount,String endNumber){

        Map<String,Object>params = new ArrayMap<>();
        params.put("number",number);
        params.put("numCount",numCount);

        HttpRequestEngine.postRequest(ConfigUrl.ADD_DESPOSIT_BOOK, params, new HttpRequestResultListener() {
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

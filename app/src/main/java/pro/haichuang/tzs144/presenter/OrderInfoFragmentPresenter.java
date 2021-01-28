package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;

public class OrderInfoFragmentPresenter {


    public OrderInfoFragmentPresenter(){

    }


    /**
     * 根据状态获取对应的订单数据
     * @param deliveryStatus
     * @param page
     */
    public void loadOrderByStatus(String deliveryStatus,int page){

        Map<String,Object> params = new ArrayMap<>();
        params.put("verification","");
        params.put("deliveryStatus",deliveryStatus);
        params.put("page",page);
        params.put("limit", Config.LIMIT);

        HttpRequestEngine.postRequest(ConfigUrl.HOME_ORDER, params, new HttpRequestResultListener() {
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

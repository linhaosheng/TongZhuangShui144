package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class OrderDetailActivityPresenter {


    public OrderDetailActivityPresenter(){

    }


    /**
     * [首页]-订单接单
     * @param verification
     * @param id
     */
    public void takeOrder(String verification,String id){
        Map<String,Object> params = new ArrayMap<>();
        params.put("verification",verification);
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.TAKE_ORDER, params, new HttpRequestResultListener() {
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
     * [首页]-订单详情
     * @param id
     * @param longitude
     * @param latitude
     */
    public void getHomeOrderInfo(String id,long longitude,long latitude ){
        Map<String,Object> params = new ArrayMap<>();
        params.put("id",id);
        params.put("longitude",longitude);
        params.put("latitude",latitude);

        HttpRequestEngine.postRequest(ConfigUrl.HOME_ORDER_INFO, params, new HttpRequestResultListener() {
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

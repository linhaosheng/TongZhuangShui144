package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class ClientDetailActivityPresenter {

    public ClientDetailActivityPresenter(){

    }

    /**
     * [客户]获取客户详情
     * @param customerId
     */
    public void getCustomerInfo(String customerId){

        Map<String,Object>params = new ArrayMap<>();
        HttpRequestEngine.postRequest(ConfigUrl.GET_CUSTOMER_INFO, params, new HttpRequestResultListener() {
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
     * [客户]修改地址
     * @param id
     * @param customerId
     * @param addressName
     * @param address
     * @param longitude
     * @param latitude
     */
    public void updateAddress(String id,String customerId,String addressName,String address,double longitude,double latitude){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        params.put("customerId",customerId);
        params.put("addressName",addressName);
        params.put("address",address);
        params.put("longitude",longitude);
        params.put("latitude",latitude);

        HttpRequestEngine.postRequest(ConfigUrl.UPDATE_ADDRESS, params, new HttpRequestResultListener() {
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
     * [客户]删除地址
     * @param id
     */
    public void delAddress(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.DEL_ADDRESS, params, new HttpRequestResultListener() {
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
     * [客户]删除维护记录
     * @param id
     */
    public void delMaintainLog(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.DEL_MAINTAIN_LOG, params, new HttpRequestResultListener() {
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

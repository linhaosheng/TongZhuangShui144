package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class SalesListActivityPresenter {

    public SalesListActivityPresenter(){

    }

    /**
     * [直接销售]-客户类型列表
     */
    public void findCustomerType(){
        HttpRequestEngine.postRequest(ConfigUrl.FIND_CUSTOME_TYPE, null, new HttpRequestResultListener() {
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
     * [直接销售]-列表数据
     * @param cutomerType
     * @param startTime
     * @param endTime
     * @param page
     */
    public void findDirectSales(String cutomerType,String startTime,String endTime,int page){

        Map<String,Object> params = new ArrayMap<>();
        params.put("cutomerType",cutomerType);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DIRECT_SALES, params, new HttpRequestResultListener() {
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

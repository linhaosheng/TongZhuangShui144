package pro.haichuang.tzs144.fragment;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;

public class MainActivityPresenter {



    public MainActivityPresenter(){

    }

    /**
     * [客户]获取客户类型
     */
    public void findKhTypes(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_KH_TYPES, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                SPUtils.putString(Config.CLIENT_TYPE,result);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * [实时库存]获取所有有效员工
     */
    public void getAllClient(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_STAFFS, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
              SPUtils.putString(Config.FIND_STAFFS,result);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * [客户]获取所在片区列表
     * @param
     */
    public void findAreas(){
        HttpRequestEngine.postRequest(ConfigUrl.FIND_AREAS, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                SPUtils.putString(Config.FIND_AREA,result);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * [实时库存]-调拨主体列表
     */
    public void findStockMainList(){
        HttpRequestEngine.postRequest(ConfigUrl.FIND_STOCK_MAIN_LIST, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                SPUtils.putString(Config.STOCK_MAIN_LIST,result);
            }

            @Override
            public void error(String error) {

            }
        });
    }
}

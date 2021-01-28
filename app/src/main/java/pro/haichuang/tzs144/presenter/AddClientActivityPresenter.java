package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AddClientActivityPresenter {


    public void AddClientActivityPresenter(){

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

            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * [客户]获取所在片区列表
     * @param parentId
     */
    public void findAreas(String parentId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("parentId",parentId);
        HttpRequestEngine.postRequest(ConfigUrl.FIND_AREAS, null, new HttpRequestResultListener() {
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
     * [客户]新增客户
     */
    public void addKh(){
        Map<String,Object>params = new ArrayMap<>();

        HttpRequestEngine.postRequest(ConfigUrl.ADD_KH, params, new HttpRequestResultListener() {
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

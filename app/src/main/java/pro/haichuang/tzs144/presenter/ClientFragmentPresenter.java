package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class ClientFragmentPresenter {

    public ClientFragmentPresenter(){

    }

    /**
     * [客户]列表统计
     */
    public void countKh(){

        HttpRequestEngine.postRequest(ConfigUrl.COUNT_KH, null, new HttpRequestResultListener() {
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
     * [客户]获取客户列表 列表搜索
     * @param khName
     * @param startTime
     * @param endTime
     * @param khTypeId
     * @param khStatus
     * @param page
     */
    public void findKhList(String khName,String startTime,String endTime,String khTypeId,String khStatus,int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("khName",khName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("khTypeId",khTypeId);
        params.put("khStatus",khStatus);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_KHLIST, params, new HttpRequestResultListener() {
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

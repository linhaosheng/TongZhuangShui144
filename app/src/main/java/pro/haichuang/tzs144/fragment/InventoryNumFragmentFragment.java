package pro.haichuang.tzs144.fragment;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class InventoryNumFragmentFragment {

    public InventoryNumFragmentFragment(){

    }

    /**
     * 根据类型 [实时库存]查询商品列表
     * @param query
     * @param categoryId
     * @param type
     */
    public void findGoodsWithType(String query,String categoryId,String type){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);
        params.put("categoryId",categoryId);
        params.put("type",type);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS, params, new HttpRequestResultListener() {
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

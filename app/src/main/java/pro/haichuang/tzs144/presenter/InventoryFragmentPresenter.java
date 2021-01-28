package pro.haichuang.tzs144.presenter;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class InventoryFragmentPresenter {

    public InventoryFragmentPresenter(){

    }

    /**
     * [实时库存]获取商品品类
     */
    public void findGoodsCategory(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS_CATEGORY, null, new HttpRequestResultListener() {
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

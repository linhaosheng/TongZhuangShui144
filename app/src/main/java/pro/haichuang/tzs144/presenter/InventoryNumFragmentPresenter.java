package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.InventoryNumModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class InventoryNumFragmentPresenter {


    private ILoadDataView iLoadDataView;

    public InventoryNumFragmentPresenter(ILoadDataView mILoadDataView){
       this.iLoadDataView = mILoadDataView;
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
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    InventoryNumModel inventoryNumModel = Utils.gsonInstane().fromJson(result, InventoryNumModel.class);
                    if (inventoryNumModel.getResult()==1){
                        iLoadDataView.successLoad(inventoryNumModel);
                    }else {
                        iLoadDataView.errorLoad("加载错误");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}

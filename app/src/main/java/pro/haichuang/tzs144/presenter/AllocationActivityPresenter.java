package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AllocationShopEvent;
import pro.haichuang.tzs144.model.AllocationShopModel;
import pro.haichuang.tzs144.model.GoodBeanModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class AllocationActivityPresenter {

    private ILoadDataView iLoadDataView;


    public AllocationActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [实时库存]调拨商品列表
     * @param query
     */
    public void findAllotGoods(String query){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ALLOT_GOODS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                AllocationShopModel allocationShopModel = Utils.gsonInstane().fromJson(result, AllocationShopModel.class);
                EventBus.getDefault().post(new AllocationShopEvent(allocationShopModel));
            }

            @Override
            public void error(String error) {

            }
        });

    }


    /**
     * [实时库存]调拨出库
     */
    public void allotGoods(String stockMainId, List<GoodBeanModel>goodBeanModels){

        Map<String,Object> params = new ArrayMap<>();
        params.put("stockMainId",stockMainId);
        params.put("goodsList",goodBeanModels);


        HttpRequestEngine.postRequest(ConfigUrl.ALLOT_GOODS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                   try {
                       JSONObject jsonObject = new JSONObject(result);
                       if (jsonObject.getInt("result")==1){
                           iLoadDataView.successLoad(jsonObject.getString("message"));
                       }else {
                           iLoadDataView.errorLoad(jsonObject.getString("message"));
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

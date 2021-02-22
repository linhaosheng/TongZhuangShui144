package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.GoodBeanModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.ShopModelEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class DemandListActivityPresenter {

    private ILoadDataView iLoadDataView;

    public DemandListActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }


    /**
     * [实时库存]录入需求单
     * @param startTime
     * @param endTime
     */
    public void demand(String startTime, String endTime, List<GoodBeanModel>list){

        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("goodsList",list);

        HttpRequestEngine.postRequest(ConfigUrl.DEMAND, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
               try {
                   JSONObject jsonObject = new JSONObject(result);
                   String message = jsonObject.getString("message");
                   if (jsonObject.getInt("result")==1){
                       iLoadDataView.successLoad(message);
                   }else {
                       iLoadDataView.errorLoad(message);
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


    /**
     * [实时库存]获取需求单商品列表
     * @param query
     */
    public void findDemandGoods(String query){
       Map<String,Object>params = new ArrayMap<>();
       params.put("query",query);
       HttpRequestEngine.postRequest(ConfigUrl.FIND_DEMAND_GOODS, params, new HttpRequestResultListener() {
           @Override
           public void start() {

           }

           @Override
           public void success(String result) {
               ShopModel shopModel = Utils.gsonInstane().fromJson(result, ShopModel.class);
               EventBus.getDefault().post(new ShopModelEvent(shopModel));
           }

           @Override
           public void error(String error) {

           }
       });
    }
}

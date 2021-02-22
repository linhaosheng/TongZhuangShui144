package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.GoodsShopEvent;
import pro.haichuang.tzs144.model.GoodsShopModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class HistoryWithDrawalOrderActivityPresenter {

    private ILoadDataView iLoadDataView;

    public HistoryWithDrawalOrderActivityPresenter(ILoadDataView mILoadDataView){
       this.iLoadDataView = mILoadDataView;
    }

    /**
     * [押金]提交退押（手动录入）
     */
    public void saveHistory(String no,String bookNo,String customerId,String goodsId,String price,String num,String type){

        Map<String,Object>params = new ArrayMap<>();
        params.put("no",no);
        params.put("bookNo",bookNo);
        params.put("customerId",customerId);
        params.put("goodsId",goodsId);
        params.put("price",price);
        params.put("num",num);
        params.put("type",type);

        HttpRequestEngine.postRequest(ConfigUrl.SAVE_HISTORY, params, new HttpRequestResultListener() {
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
     * [押金]查询开押商品
     */
    public final void findDepositGoods(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_GOODS, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                GoodsShopModel goodsShopModel = Utils.gsonInstane().fromJson(result, GoodsShopModel.class);
                EventBus.getDefault().post(new GoodsShopEvent(goodsShopModel));
            }

            @Override
            public void error(String error) {

            }
        });
    }
}

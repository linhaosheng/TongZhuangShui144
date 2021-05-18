package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class OrderDetailPresenter {

    private ILoadDataView iLoadDataView;


    public OrderDetailPresenter(ILoadDataView iLoadDataView){
         this.iLoadDataView = iLoadDataView;
    }

    /**
     * [直接销售]-订单详情
     * @param id
     */
    public final void getOrderInfo(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.GET_ORDER_INFO, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                OrderDetailModel orderDetailModel = Utils.gsonInstane().fromJson(result, OrderDetailModel.class);
                if (orderDetailModel.getResult()==1){
                    iLoadDataView.successLoad(orderDetailModel.getData());
                }else {
                    iLoadDataView.errorLoad("加载错误");
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [首页]-转订单
     */
    public void turnOrder(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.TURN_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,4));
                    }else {
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,4));
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
     * [首页]-作废订单
     * @param id
     */
    public void directSelling(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.DIRECT_SELLING, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,2));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,2);
                        String message = jsonObject.getString("message");
                        statusEvent.setResult(message);
                        EventBus.getDefault().post(statusEvent);
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
     * 订单接单
     * @param id
     */
    public void takeOrder(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.TAKE_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,1));
                    }else {
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,1));
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
     * 配送
     * @param id
     */
    public void deliveryOrder(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);


        HttpRequestEngine.postRequest(ConfigUrl.DELIVERY_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,3));
                    }else {
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,3));
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
     * [直接销售]-加载绑定回收材料
     */
    public final void findGoodsMaterial(String goodsId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("goodsId",goodsId);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS_MATERIAL, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });

    }
}

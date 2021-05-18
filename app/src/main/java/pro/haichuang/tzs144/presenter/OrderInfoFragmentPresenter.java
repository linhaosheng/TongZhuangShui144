package pro.haichuang.tzs144.presenter;

import android.content.Intent;
import android.net.Uri;
import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.StatusUpdateEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class OrderInfoFragmentPresenter {

    public ILoadDataView iLoadDataView;

    public OrderInfoFragmentPresenter(ILoadDataView mILoadDataView){
          this.iLoadDataView = mILoadDataView;
    }


    /**
     * 根据状态获取对应的订单数据
     * @param deliveryStatus
     * @param page
     */
    public void loadOrderByStatus(int deliveryStatus,String queryTime,int page){

        Map<String,Object> params = new ArrayMap<>();
        params.put("deliveryStatus",deliveryStatus);
        if (deliveryStatus==4 && queryTime!=null){
            params.put("queryTime",queryTime);
        }
        params.put("page",page);
        params.put("limit", Config.LIMIT);

        HttpRequestEngine.postRequest(ConfigUrl.HOME_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    OrderInfoModel orderInfoModel = Utils.gsonInstane().fromJson(result, OrderInfoModel.class);
                    if (orderInfoModel!=null && orderInfoModel.getResult()==1){
                        iLoadDataView.successLoad(orderInfoModel.getData());
                    }else {
                        iLoadDataView.errorLoad("获取错误");
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
     * [首页]-知道了
     */
    public void setIsKnow(String id,int currentId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.SET_IS_KNOW, params, new HttpRequestResultListener() {
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
                        EventBus.getDefault().post(new StatusUpdateEvent(Config.LOAD_SUCCESS,currentId));
                    }else {
                        EventBus.getDefault().post(new StatusUpdateEvent(Config.LOAD_FAIL,currentId));
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
    public void takeOrder(String id,int currentId){

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
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,currentId));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,currentId);
                        statusEvent.setResult(jsonObject.getString("message"));
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

}

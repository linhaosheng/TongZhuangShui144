package pro.haichuang.tzs144.presenter;

import android.text.TextUtils;
import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountOrderModel;
import pro.haichuang.tzs144.model.AccountRealTimeModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class ClientRealTimeDatapPresenter {

    private ILoadDataView iLoadDataView;

    public ClientRealTimeDatapPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [账务]账务管理统计
     */
    public final void ssManagerCount(String categoryName,String categoryId){
        Map<String,Object>params = null;

        if (!"全部".equals(categoryName)){
            params = new ArrayMap<>();
            params.put("categoryName",categoryName);
            params.put("categoryId",categoryId);
        }

        HttpRequestEngine.postRequest(ConfigUrl.MANAGER_COUNT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                AccountRealTimeModel accountRealTimeModel = Utils.gsonInstane().fromJson(result, AccountRealTimeModel.class);
                if (accountRealTimeModel.getResult()==1){
                    iLoadDataView.successLoad(accountRealTimeModel.getData());
                }else {
                    iLoadDataView.errorLoad("获取数据错误");
                }
            }

            @Override
            public void error(String error) {

            }
        });
    }


    /**
     *  [账务]账务管理-查询订单列表
     * @param startTime
     * @param endTime
     */
    public final void findSsOrders(String startTime,String endTime,int page,String categoryName,String categoryId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("page",page);

        if (!"全部".equals(categoryName)){
            params.put("categoryName",categoryName);
            params.put("categoryId",categoryId);
        }

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SS_ORDERS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountOrderModel accountOrderModel = Utils.gsonInstane().fromJson(result, AccountOrderModel.class);
                if (accountOrderModel.getResult()==1){
                    EventBus.getDefault().post(new RealAccountEvent(accountOrderModel,1));
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [账务]账务管理 - 结账
     */
    public final void settle(List<Integer>orderIds,String categoryName,String categoryId){
        Utils.showCenterTomast("正在结账");
        Map<String,Object>params = new ArrayMap<>();
        if (orderIds!=null && orderIds.size()>0){
            params.put("orderIds",orderIds);
        }
        if (!"全部".equals(categoryName)){
            params.put("categoryName",categoryName);
            params.put("categoryId",categoryId);
        }

        HttpRequestEngine.postRequest(ConfigUrl.SETTLE, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //结账成功
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,100));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,100);
                        statusEvent.setResult(jsonObject.getString("message"));
                        EventBus.getDefault().post(statusEvent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,4));
            }
        });
    }

    /**
     * [账务]账务管理 - 作废
     */
    public final void cancel(int id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.ACCOUNT_CANCEL, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //作废成功
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,5));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,5);
                        statusEvent.setResult(jsonObject.getString("message"));
                        EventBus.getDefault().post(statusEvent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,5));
            }
        });
    }
}

package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class ClientDetailActivityPresenter {


    private ILoadDataView iLoadDataView;

    public ClientDetailActivityPresenter(ILoadDataView mILoadDataView){
        this.iLoadDataView = mILoadDataView;
    }

    /**
     * [客户]获取客户详情
     * @param customerId
     */
    public void getCustomerInfo(String customerId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("customerId",customerId);

        HttpRequestEngine.postRequest(ConfigUrl.GET_CUSTOMER_INFO, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {

                ClientDetailModel clientDetailModel = Utils.gsonInstane().fromJson(result, ClientDetailModel.class);
                if (clientDetailModel!=null && clientDetailModel.getResult()==1){
                    iLoadDataView.successLoad(clientDetailModel.getData());
                }else {
                    iLoadDataView.errorLoad("获取失败");
                }

            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [客户]修改地址
     * @param id
     * @param customerId
     * @param addressName
     * @param address
     * @param longitude
     * @param latitude
     */
    public void updateAddress(int id,String customerId,String addressName,String address,double longitude,double latitude){

        Map<String,Object>params = new ArrayMap<>();
        if (id!=0){
            params.put("id",id);
        }
        params.put("customerId",customerId);
        params.put("addressName",addressName);
        params.put("address",address);
        params.put("longitude",longitude);
        params.put("latitude",latitude);

        HttpRequestEngine.postRequest(ConfigUrl.UPDATE_ADDRESS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,3));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,3);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,3));
            }
        });
    }

    /**
     * [客户]删除地址
     * @param id
     */
    public void delAddress(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.DEL_ADDRESS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,0));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,0);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,0));
            }
        });
    }

    /**
     * [客户]删除维护记录
     * @param id
     */
    public void delMaintainLog(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.DEL_MAINTAIN_LOG, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,1));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,1);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,1));
            }
        });
    }

    /**
     * [客户]新增/编辑维护记录
     * @param id
     */
    public void saveMaintainLog(int id,String customerId,String maintainInfo,double distance,String time){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        params.put("customerId",customerId);
        params.put("maintainInfo",maintainInfo);
        params.put("distance",distance);
        params.put("time",time);

        HttpRequestEngine.postRequest(ConfigUrl.SAVE_MAINTAIN_LOG, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,2));
            }
        });
    }
}

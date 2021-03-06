package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

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
    public void updateAddress(String id,String customerId,String addressName,String address,double longitude,double latitude){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
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

            }

            @Override
            public void error(String error) {

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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,0));
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

            }

            @Override
            public void error(String error) {

            }
        });
    }
}

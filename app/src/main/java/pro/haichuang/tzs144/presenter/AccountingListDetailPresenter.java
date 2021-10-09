package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountListDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class AccountingListDetailPresenter {

    private ILoadDataView iLoadDataView;

    public AccountingListDetailPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
    }

    /**
     * [账目]账目详情
     * @param id
     */
    public final void getAccountInfo(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.GET_ACCOUNT_INFOv, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountListDetailModel accountListDetailModel = Utils.gsonInstane().fromJson(result, AccountListDetailModel.class);
                if (accountListDetailModel!=null && accountListDetailModel.getResult()==1){
                    iLoadDataView.successLoad(accountListDetailModel.getData());
                }else {
                    iLoadDataView.errorLoad(accountListDetailModel.getMessage());
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });

    }

    /**
     * [账目]销账
     */
    public final void cancelAccount(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.CANCEL_ACCOUNT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,7));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,7);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,7));
            }
        });
    }

    /**
     * [账务]账务管理 - 结账
     */
    public final void settle(){

        HttpRequestEngine.postRequest(ConfigUrl.SETTLE, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //结账成功
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,8));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,8);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,8));
            }
        });
    }
}

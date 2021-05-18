package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class AccountingListPresenter {


    private ILoadDataView iLoadDataView;

    public AccountingListPresenter(ILoadDataView iLoadDataView){
      this.iLoadDataView = iLoadDataView;
    }

    /**
     * [账目]账目列表
     * @param startTime
     * @param endTime
     */
    public final void findOrderAccounts(String startTime,String endTime){

        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ORDER_ACCOUNT, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountListModel accountListModel = Utils.gsonInstane().fromJson(result, AccountListModel.class);
                if (accountListModel.getResult()==1){
                    iLoadDataView.successLoad(accountListModel.getData());
                }else {
                    iLoadDataView.errorLoad("获取错误");
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
                      EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,6));
                  }else {
                      StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,6);
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
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,6));
            }
        });
    }
}

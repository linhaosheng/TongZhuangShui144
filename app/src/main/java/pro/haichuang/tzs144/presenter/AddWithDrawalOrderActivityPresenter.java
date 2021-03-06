package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class AddWithDrawalOrderActivityPresenter {

    private ILoadDataView iLoadDataView;

    public AddWithDrawalOrderActivityPresenter(ILoadDataView iLoadDataView){
      this.iLoadDataView = iLoadDataView;
    }

    /**
     * [押金]提交退押
     * @param ids
     */
    public void returnDeposits(String ids,String returnCount,String returnPrice){
        Map<String,Object>params = new ArrayMap<>();
        params.put("ids",ids);
        params.put("returnCount",returnCount);
        params.put("returnPrice",returnPrice);

        HttpRequestEngine.postRequest(ConfigUrl.RETURN_DEPOSIT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
              try {
                  JSONObject jsonObject = new JSONObject(result);
                  String message = jsonObject.getString("message");
                  if (jsonObject.getInt("result")==1){
                      EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,9));
                  }else {
                      StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,9);
                      statusEvent.setResult(message);
                      EventBus.getDefault().post(statusEvent);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,9));
            }
        });
    }

    /**
     * [押金]新增退押 - 列表数据
     * @param yjNo
     * @param
     */
    public void findByKhReturnDeposits(String yjNo,int page){
        Map<String,Object>params = new ArrayMap<>();
        params.put("yjNo",yjNo);
        params.put("page",String.valueOf(page));
        params.put("limit",10);


        HttpRequestEngine.postRequest(ConfigUrl.FIND_BY_RETURN_DEPOSITS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                WithDrawalOrderModel withDrawalOrderModel = Utils.gsonInstane().fromJson(result, WithDrawalOrderModel.class);
                if (withDrawalOrderModel.getResult()==1){
                    iLoadDataView.successLoad(withDrawalOrderModel.getData());
                }else {
                    iLoadDataView.errorLoad(withDrawalOrderModel.getMessage());
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}

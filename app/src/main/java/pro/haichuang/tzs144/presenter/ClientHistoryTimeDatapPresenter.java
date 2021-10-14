package pro.haichuang.tzs144.presenter;

import android.text.TextUtils;
import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountHistoryModel;
import pro.haichuang.tzs144.model.AccountOrderModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class ClientHistoryTimeDatapPresenter {

    private ILoadDataView iLoadDataView;

    public ClientHistoryTimeDatapPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [账务]历史订单统计
     */
    public final void countLsOrder(String time,String startTime,String endTime){

        Map<String,Object>params = new ArrayMap<>();
        if (!TextUtils.isEmpty(time)){
            params.put("time",time);
        }
        if (!TextUtils.isEmpty(startTime)){
            params.put("startTime",startTime);
        }
        if (!TextUtils.isEmpty(endTime)){
            params.put("endTime",endTime);
        }


        HttpRequestEngine.postRequest(ConfigUrl.COUNT_LS_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                AccountHistoryModel accountHistoryModel =   Utils.gsonInstane().fromJson(result, AccountHistoryModel.class);
                if (accountHistoryModel!=null && accountHistoryModel.getResult()==1){
                    iLoadDataView.successLoad(accountHistoryModel.getData());
                }else {
                    iLoadDataView.errorLoad("获取错误");
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
    public final void findLsOrders(String time,String startTime,String endTime,int page){

        Map<String,Object>params = new ArrayMap<>();
        if (time!=null) {
            params.put("time",time);
        }else {
            params.put("startTime",startTime);
            params.put("endTime",endTime);
        }
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_LS_ORDERS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountOrderModel accountOrderModel = Utils.gsonInstane().fromJson(result, AccountOrderModel.class);
                if (accountOrderModel.getResult()==1){
                    EventBus.getDefault().post(new RealAccountEvent(accountOrderModel,2));
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}

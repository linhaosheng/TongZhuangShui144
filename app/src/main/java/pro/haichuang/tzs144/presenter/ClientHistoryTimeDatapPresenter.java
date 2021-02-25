package pro.haichuang.tzs144.presenter;

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
    public final void countLsOrder(String time){

        Map<String,Object>params = new ArrayMap<>();
        params.put("time",time);

        HttpRequestEngine.postRequest(ConfigUrl.COUNT_LS_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountHistoryModel accountHistoryModel =   Utils.gsonInstane().fromJson(result, AccountHistoryModel.class);
                if (accountHistoryModel!=null && accountHistoryModel.getResult()==1){
                    AccountHistoryModel.DataBean data = accountHistoryModel.getData();
                    data.setCouponPrice("0.0");
                    data.setCouponDayRatio("0");
                    data.setCouponWeekRatio("0");
                    accountHistoryModel.setData(data);
                    iLoadDataView.successLoad(accountHistoryModel.getData());
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
     *  [账务]账务管理-查询订单列表
     * @param startTime
     * @param endTime
     */
    public final void findLsOrders(String time,String startTime,String endTime){

        Map<String,Object>params = new ArrayMap<>();
        params.put("time",time);
        params.put("startTime",startTime);
        params.put("endTime",endTime);


        HttpRequestEngine.postRequest(ConfigUrl.FIND_LS_ORDERS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                AccountOrderModel accountOrderModel = Utils.gsonInstane().fromJson(result, AccountOrderModel.class);
                if (accountOrderModel.getResult()==1){
                    EventBus.getDefault().post(new RealAccountEvent(accountOrderModel));
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}

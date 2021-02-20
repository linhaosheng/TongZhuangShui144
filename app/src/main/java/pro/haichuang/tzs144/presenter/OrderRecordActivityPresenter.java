package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderRecordModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class OrderRecordActivityPresenter {

    private ILoadDataView iLoadDataView;


    public OrderRecordActivityPresenter(ILoadDataView mILoadDataView){
        this.iLoadDataView = mILoadDataView;
    }

    /**
     * [客户]获取客户订单记录
     * @param customerId
     * @param startTime
     * @param endTime
     * @param page
     */
    public void findCustomerOrders(String customerId,String startTime,String endTime,int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("customerId",customerId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FINF_CUSTOMER_ORDERS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                OrderRecordModel orderRecordModel = Utils.gsonInstane().fromJson(result, OrderRecordModel.class);
                if (orderRecordModel.getResult()==1){
                    iLoadDataView.successLoad(orderRecordModel.getData());
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
}

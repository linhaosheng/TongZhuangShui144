package pro.haichuang.tzs144.presenter;

import android.content.Intent;
import android.net.Uri;
import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderInfoModel;
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
        params.put("queryTime",queryTime);
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
                iLoadDataView.successLoad(result);
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

}

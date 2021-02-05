package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleListModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class SalesListActivityPresenter {

    private ILoadDataView iLoadDataView;

    public SalesListActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [直接销售]-客户类型列表
     */
    public void findCustomerType(){
        HttpRequestEngine.postRequest(ConfigUrl.FIND_CUSTOME_TYPE, null, new HttpRequestResultListener() {
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
     * [直接销售]-列表数据
     * @param cutomerType
     * @param startTime
     * @param endTime
     * @param page
     */
    public void findDirectSales(String cutomerType,String startTime,String endTime,int page){

        Map<String,Object> params = new ArrayMap<>();
        params.put("cutomerType",cutomerType);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DIRECT_SALES, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                  iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    SaleListModel saleListModel = Utils.gsonInstane().fromJson(result, SaleListModel.class);
                    if (saleListModel.getResult()==1){
                        iLoadDataView.successLoad(saleListModel.getData());
                    }else {
                        iLoadDataView.errorLoad("获取错误");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
              iLoadDataView.errorLoad(error);
            }
        });
    }
}

package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class SelectAddressActivityPresenter {

    private ILoadDataView iLoadDataView;

    public SelectAddressActivityPresenter(ILoadDataView iLoadDataView){
      this.iLoadDataView = iLoadDataView;
    }

    /**
     * 客户地址列表
     * @param customerId
     */
    public void findAddress(String customerId){

        Map<String,Object> params = new ArrayMap<>();
        params.put("customerId",customerId);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ADDRESS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                SaleDataModel saleDataModel = Utils.gsonInstane().fromJson(result, SaleDataModel.class);
                iLoadDataView.successLoad(saleDataModel.getData());
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });

    }
}

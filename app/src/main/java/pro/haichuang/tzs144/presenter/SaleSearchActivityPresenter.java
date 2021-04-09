package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class SaleSearchActivityPresenter {


    private ILoadDataView iLoadDataView;

    public SaleSearchActivityPresenter(ILoadDataView iLoadDataView){
      this.iLoadDataView = iLoadDataView;
    }

    /**
     * 客户搜索
     * @param search
     */
    public void search(String search,String type){

        Map<String,Object>params = new ArrayMap<>();
        params.put("search",search);

        if ("add_with_drawal".equals(type)){
            params.put("type","0");
        }

        HttpRequestEngine.postRequest(ConfigUrl.SEARCH, params, new HttpRequestResultListener() {
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

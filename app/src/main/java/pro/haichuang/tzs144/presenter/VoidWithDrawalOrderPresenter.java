package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class VoidWithDrawalOrderPresenter {

    private ILoadDataView iLoadDataView;

    public VoidWithDrawalOrderPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }


    /**
     * [押金]获取作废退押单
     * @param query
     * @param page
     */
    public final void findCancelList(String query,int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_CANCEL_LIST, params, new HttpRequestResultListener() {
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

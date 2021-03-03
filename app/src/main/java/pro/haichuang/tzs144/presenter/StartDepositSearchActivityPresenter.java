package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class StartDepositSearchActivityPresenter {

    private ILoadDataView iLoadDataView;

    public StartDepositSearchActivityPresenter(ILoadDataView mILoadDataView){
       this.iLoadDataView = mILoadDataView;
    }

    /**
     * [押金]获取开押列表
     * @param query
     */
    public void findDepositBookList(String query,int page ){
        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_LIST, params, new HttpRequestResultListener() {
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

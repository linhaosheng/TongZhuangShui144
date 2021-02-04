package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class HistoryWithDrawalOrderActivityPresenter {

    private ILoadDataView iLoadDataView;

    public HistoryWithDrawalOrderActivityPresenter(ILoadDataView mILoadDataView){
       this.iLoadDataView = mILoadDataView;
    }


    /**
     * [押金]提交退押（手动录入）
     */
    public void backDepositFill(){

        Map<String,Object>params = new ArrayMap<>();

        HttpRequestEngine.postRequest(ConfigUrl.BACK_DEPOSIT_FILL, params, new HttpRequestResultListener() {
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
}

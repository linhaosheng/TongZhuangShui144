package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class DepositManagementSearchActivityPresenter {

    private ILoadDataView iLoadDataView;

    public DepositManagementSearchActivityPresenter(ILoadDataView mILoadDataView){
         this.iLoadDataView = mILoadDataView;

    }


    /**
     * [押金]获取开押本列表
     */
    public void findDepositBookList(int page,long startTime,long endTime){
        Map<String,Object>params = new ArrayMap<>();
        params.put("page",page);
        params.put("startTime",startTime);
        params.put("endTime",endTime);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_BOOK_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }


}

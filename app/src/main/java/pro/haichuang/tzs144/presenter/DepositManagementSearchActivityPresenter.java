package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DepositManagerModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class DepositManagementSearchActivityPresenter {

    private ILoadDataView iLoadDataView;

    public DepositManagementSearchActivityPresenter(ILoadDataView mILoadDataView){
         this.iLoadDataView = mILoadDataView;

    }


    /**
     * [押金]获取开押本列表
     */
    public void findDepositBookList(String query,int page,String startTime,String endTime){
        Map<String,Object>params = new ArrayMap<>();
        params.put("page",page);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("query",query);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_BOOK_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                DepositManagerModel depositManagerModel = Utils.gsonInstane().fromJson(result, DepositManagerModel.class);
                if (depositManagerModel!=null & depositManagerModel.getResult()==1){
                    iLoadDataView.successLoad(depositManagerModel.getData());
                }else {
                    iLoadDataView.errorLoad("加载错误");
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }


}

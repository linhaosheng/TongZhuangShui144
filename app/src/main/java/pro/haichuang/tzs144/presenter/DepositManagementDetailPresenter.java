package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DeposiDetailModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class DepositManagementDetailPresenter {

    private ILoadDataView iLoadDataView;

    public DepositManagementDetailPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [押金]押金本详情
     * @param id
     */
    public final void getDepositBookInfo(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.DEPOSIT_BOOK_INFO, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                DeposiDetailModel deposiDetailModel = Utils.gsonInstane().fromJson(result, DeposiDetailModel.class);
                if (deposiDetailModel.getResult()==1){
                    iLoadDataView.successLoad(result);
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

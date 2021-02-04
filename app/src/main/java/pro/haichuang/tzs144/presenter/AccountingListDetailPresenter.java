package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountListDetailModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class AccountingListDetailPresenter {

    private ILoadDataView iLoadDataView;

    public AccountingListDetailPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
    }

    /**
     * [账目]账目详情
     * @param id
     */
    public final void getAccountInfo(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.GET_ACCOUNT_INFOv, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AccountListDetailModel accountListDetailModel = Utils.gsonInstane().fromJson(result, AccountListDetailModel.class);
                if (accountListDetailModel!=null && accountListDetailModel.getResult()==1){
                    iLoadDataView.successLoad(accountListDetailModel);
                }else {
                    iLoadDataView.errorLoad("获取错误");
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });

    }

}

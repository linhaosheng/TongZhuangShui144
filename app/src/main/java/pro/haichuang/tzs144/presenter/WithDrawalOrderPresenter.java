package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class WithDrawalOrderPresenter {

    private ILoadDataView iLoadDataView;


    public WithDrawalOrderPresenter(ILoadDataView iLoadDataView){
         this.iLoadDataView = iLoadDataView;
    }


    /**
     * [押金]退押订单列表
     * @param query
     */
    public final void findReturnDeposits(String query,int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);
        params.put("page",page+"");
        params.put("limit","10");

        HttpRequestEngine.postRequest(ConfigUrl.FIND_RETURN_DEPOSITS, params, new HttpRequestResultListener() {
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

    /**
     * [押金]作废退押单
     * @param id
     */
    public void cancelReturnDeposit(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.CANCEL_RETURN_DEPOSIT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {

                WithDrawalOrderModel withDrawalOrderModel = Utils.gsonInstane().fromJson(result, WithDrawalOrderModel.class);
                if (withDrawalOrderModel.getResult()==1){
                    EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,20));
                }else {
                    EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,20));
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}

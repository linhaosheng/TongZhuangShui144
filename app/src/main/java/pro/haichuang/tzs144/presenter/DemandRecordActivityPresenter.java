package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DemandRecordModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class DemandRecordActivityPresenter {

    private ILoadDataView iLoadDataView;

    public DemandRecordActivityPresenter(ILoadDataView iLoadDataVie){
       this.iLoadDataView = iLoadDataVie;
    }


    /**
     * [实时库存]查询需求单
     * @param page
     */
    public void findDemands(int page){
        Map<String,Object>params = new ArrayMap<>();
        params.put("page",page);
        params.put("limit", Config.LIMIT);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEMAND, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                DemandRecordModel demandRecordModel = Utils.gsonInstane().fromJson(result, DemandRecordModel.class);
                if (demandRecordModel.getResult()==1){
                    iLoadDataView.successLoad(demandRecordModel.getData());
                }else {
                    iLoadDataView.successLoad(demandRecordModel.getMessage());
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}
